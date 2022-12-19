import com.tdbot.runtime.TdRuntime
import com.tdbot.scenario.*

fun main() {
    TdRuntime().start {
        "CheckMyEnglishMessages" to GrammarChecker

        "ShortenMyOutgoingUrls" to BitlyUrlShortener()

        "TranscribeMyVoiceNotes" to TranscriberBot("english")

        "CataasPhoto" to ReplyWithPhotoOnMessage("mew+") {
            "https://cataas.com/cat?x=${System.currentTimeMillis()}"
        }

        "CatchContact" to CatchContact(tdBotApi)
    }
}