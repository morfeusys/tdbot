## Gramlin server

> Gramlin is a **programmable Telegram userbot** (_not a regular chatbot_) that adds extra features to your Telegram account.

Gramlin server enables you to use Gramlin without need to code any feature on Kotlin.

### How to install

0. Download and unzip the latest Gramlin release

#### Windows

1. Install [Microsoft Visual C++ Redistributable](https://aka.ms/vs/17/release/vc_redist.x64.exe)
2. Install [Java 8 or higher](https://learn.microsoft.com/en-us/java/openjdk/download)

#### MacOS and Linux

1. Install openssl3 and zlib

```
brew install openssl@3
ln -sf /usr/local/Cellar/openssl@3/3.0.0 /usr/local/opt/openssl
```

#### Configure

1. Obtain _api_id_ and _api_hash_ from [my.telegram.org](https://my.telegram.org/) and paste it into _gramlin.properties_ file
2. Register a new Telegram bot via [@BotFather](https://t.me/botfather) and paste its _token_ into _gramlin.properties_ file

### How to start

1. Run one of _run_ (for MacOS and Linux) or _run.bat_ (for Windows) script from your Gramlin home directory.
2. Go to your Telegram client and find your Gramlin bot created on step 2 of _Configure_ chapter
3. Click on _Start_ button or type _/start_ message
4. Follow the instructions of Gramlin bot to sign in and start using Gramlin

> Once you've signed in via Gramlin bot, it ignores messages from any other Telegram user for security reason

### How to use

Once you've started Gramlin with one of start script, it reads your scenarios configurations from _scenarios_ directory.
