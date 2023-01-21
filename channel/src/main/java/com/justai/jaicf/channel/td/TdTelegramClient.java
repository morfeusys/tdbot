package com.justai.jaicf.channel.td;

import it.tdlight.client.*;
import it.tdlight.common.*;
import it.tdlight.common.internal.CommonClientManager;
import it.tdlight.common.utils.CantLoadLibrary;
import it.tdlight.common.utils.LibraryVersion;
import it.tdlight.jni.TdApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class TdTelegramClient implements Authenticable {

    public static final Logger LOG = LoggerFactory.getLogger(TdTelegramClient.class);
    public static ExecutorService scannerExecutor = Executors.newSingleThreadExecutor();

    private final ExecutorService updateHandlerExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService resultHandlerExecutor = Executors.newSingleThreadExecutor();

    static {
        try {
            Init.start();
        } catch (CantLoadLibrary e) {
            throw new RuntimeException("Can't load native libraries", e);
        }
    }

    private final TelegramClient client;
    private final TDLibSettings settings;
    private AuthenticationData authenticationData;

    private final Map<String, Set<CommandHandler>> commandHandlers = new ConcurrentHashMap<>();
    private final Set<ResultHandler<TdApi.Update>> updateHandlers = new ConcurrentHashMap<ResultHandler<TdApi.Update>, Object>().keySet(
            new Object());
    private final Set<ExceptionHandler> updateExceptionHandlers = new ConcurrentHashMap<ExceptionHandler, Object>().keySet(
            new Object());
    private final Set<ExceptionHandler> defaultExceptionHandlers = new ConcurrentHashMap<ExceptionHandler, Object>().keySet(
            new Object());

    private final CountDownLatch closed = new CountDownLatch(1);

    public TdTelegramClient(TDLibSettings settings) {
        this(settings, null);
    }

    public TdTelegramClient(TDLibSettings settings, ClientInteraction clientInteraction) {
        this.client = CommonClientManager.create(LibraryVersion.IMPLEMENTATION_NAME);
        this.settings = settings;

        ClientInteraction ci = clientInteraction;
        if (ci == null) ci = new ScannerClientInteraction(this);

        addUpdateHandler(TdApi.UpdateAuthorizationState.class,
                new AuthorizationStateWaitTdlibParametersHandler(client, settings, this::handleDefaultException)
        );
        addUpdateHandler(TdApi.UpdateAuthorizationState.class,
                new AuthorizationStateWaitAuthenticationDataHandler(client, this, this::handleDefaultException)
        );
        addUpdateHandler(TdApi.UpdateAuthorizationState.class,
                new AuthorizationStateWaitRegistrationHandler(client, ci, this::handleDefaultException)
        );
        addUpdateHandler(TdApi.UpdateAuthorizationState.class,
                new AuthorizationStateWaitPasswordHandler(client, ci, this::handleDefaultException)
        );
        addUpdateHandler(TdApi.UpdateAuthorizationState.class,
                new AuthorizationStateWaitOtherDeviceConfirmationHandler(ci)
        );
        addUpdateHandler(TdApi.UpdateAuthorizationState.class,
                new AuthorizationStateWaitCodeHandler(client, ci, this::handleDefaultException)
        );
        addUpdateHandler(TdApi.UpdateAuthorizationState.class, new AuthorizationStateWaitForExit(this.closed));
    }

    @Override
    public void getAuthenticationData(Consumer<AuthenticationData> result) {
        if (authenticationData instanceof ConsoleInteractiveAuthenticationData) {
            ConsoleInteractiveAuthenticationData consoleInteractiveAuthenticationData
                    = (ConsoleInteractiveAuthenticationData) authenticationData;
            try {
                scannerExecutor.execute(() -> {
                    consoleInteractiveAuthenticationData.askData();
                    result.accept(consoleInteractiveAuthenticationData);
                });
            } catch (RejectedExecutionException | NullPointerException ex) {
                LOG.error("Failed to execute askData. Returning an empty string", ex);
                result.accept(consoleInteractiveAuthenticationData);
            }
        } else {
            result.accept(authenticationData);
        }
    }

    public void start(AuthenticationData authenticationData) {
        this.authenticationData = authenticationData;
        createDirectories();
        client.initialize(this::handleUpdate, this::handleUpdateException, this::handleDefaultException);
    }

    public <T extends TdApi.Update> void addCommandHandler(String commandName, CommandHandler handler) {
        Set<CommandHandler> handlers = this.commandHandlers.computeIfAbsent(commandName,
                k -> new ConcurrentHashMap<CommandHandler, Object>().keySet(new Object())
        );
        handlers.add(handler);
    }

    @SuppressWarnings("unchecked")
    public <T extends TdApi.Update> void addUpdateHandler(Class<T> updateType, GenericUpdateHandler<T> handler) {
        int updateConstructor = ConstructorDetector.getConstructor(updateType);
        this.updateHandlers.add(update -> {
            if (update.getConstructor() == updateConstructor) {
                updateHandlerExecutor.execute(() -> handler.onUpdate((T) update));
            }
        });
    }

    public void addUpdatesHandler(GenericUpdateHandler<TdApi.Update> handler) {
        this.updateHandlers.add(update -> {
            if (update instanceof TdApi.Update) {
                handler.onUpdate((TdApi.Update) update);
            } else {
                LOG.warn("Unknown update type: {}", update);
            }
        });
    }

    public void addUpdateExceptionHandler(ExceptionHandler updateExceptionHandler) {
        this.updateExceptionHandlers.add(updateExceptionHandler);
    }

    public void addDefaultExceptionHandler(ExceptionHandler defaultExceptionHandlers) {
        this.defaultExceptionHandlers.add(defaultExceptionHandlers);
    }

    private void createDirectories() {
        try {
            if (Files.notExists(settings.getDatabaseDirectoryPath())) {
                Files.createDirectories(settings.getDatabaseDirectoryPath());
            }
            if (Files.notExists(settings.getDownloadedFilesDirectoryPath())) {
                Files.createDirectories(settings.getDownloadedFilesDirectoryPath());
            }
        } catch (IOException ex) {
            throw new UncheckedIOException("Can't create TDLight directories", ex);
        }
    }

    public <R extends TdApi.Object> void send(TdApi.Function<R> function, GenericResultHandler<R> resultHandler) {
        client.send(
                function,
                result -> resultHandlerExecutor.execute(() -> resultHandler.onResult(Result.of(result))),
                this::handleResultHandlingException
        );
    }

    public void sendClose() {
        client.send(new TdApi.Close(), ok -> {
            if (ok.getConstructor() == TdApi.Error.CONSTRUCTOR) {
                throw new TelegramError((TdApi.Error) ok);
            }
        });
    }

    public void closeAndWait() throws InterruptedException {
        client.send(new TdApi.Close(), ok -> {
            if (ok.getConstructor() == TdApi.Error.CONSTRUCTOR) {
                throw new TelegramError((TdApi.Error) ok);
            }
        });
        this.waitForExit();
    }

    public void waitForExit() throws InterruptedException {
        closed.await();
    }

    private void handleUpdate(TdApi.Object update) {
        boolean handled = false;
        for (ResultHandler<TdApi.Update> updateHandler : updateHandlers) {
            updateHandler.onResult(update);
            handled = true;
        }
        if (!handled) {
            LOG.warn("An update was not handled, please use addUpdateHandler(handler) before starting the client!");
        }
    }

    private void handleUpdateException(Throwable ex) {
        boolean handled = false;
        for (ExceptionHandler updateExceptionHandler : updateExceptionHandlers) {
            updateExceptionHandler.onException(ex);
            handled = true;
        }
        if (!handled) {
            LOG.warn("Error received from Telegram!", ex);
        }
    }

    private void handleDefaultException(Throwable ex) {
        boolean handled = false;
        for (ExceptionHandler exceptionHandler : defaultExceptionHandlers) {
            exceptionHandler.onException(ex);
            handled = true;
        }
        if (!handled) {
            LOG.warn("Unhandled exception!", ex);
        }
    }

    private void handleResultHandlingException(Throwable ex) {
        LOG.error("Failed to handle the request result", ex);
    }
}
