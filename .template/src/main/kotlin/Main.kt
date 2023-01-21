import com.justai.gramlin.api.*
import com.justai.gramlin.runtime.GramlinRuntime
import com.justai.gramlin.scenario.*

fun main() {
    GramlinRuntime().start {
        "UrlShortener" to ShortenMyUrlsWithBitlyBot()

        "ContactCatcher" to CatchContact

        "VoiceTranscriber" to TranscribeMyVoiceNotesWithTranscriberBot("english")

        "CataasPhoto" to ReactOnMessage("m+e+w+") {
            reactions.image("https://cataas.com/cat?x=${System.currentTimeMillis()}")
        }

        "SpellChecker" to FixMySpellWithYandexSpeller

        "ContactsStatistics" to CollectContactUsageStats()

        "SendReasonOnCallDecline" to SendReasonOnCallDecline(
            "Can't answer now, sorry. Please send me a text message."
        )
    }
}