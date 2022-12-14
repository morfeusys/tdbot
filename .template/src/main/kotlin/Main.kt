import com.tdbot.runtime.TdRuntime
import com.tdbot.scenario.*

fun main() {
    TdRuntime().start {
        "CheckMyEnglishMessages" to GrammarChecker

        "ShortenMyOutgoingUrls" to BitlyUrlShortener()

        "TranscribeMyVoiceNotes" to TranscriberBot("english")

        "SendCatPhoto" to SendPhotoOnMyMessage("mew+") {
            "https://cataas.com/cat?color=${System.currentTimeMillis()}"
        }

        "ReplyWithCatPhoto" to ReplyWithPhoto(pattern = "mew+") {
            "https://cataas.com/cat?color=${System.currentTimeMillis()}"
        }

        "CatchContact" to CatchContact(tdBotApi)
    }
}