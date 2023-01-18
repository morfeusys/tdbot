package com.tdbot.server.scenarios

import com.tdbot.scenario.TranscribeMyVoiceNotesWithTranscriberBot
import com.tdbot.server.TdScenarioFactory

data class TranscriberBotVoiceTranscriberConfig(val language: String)

val TranscriberBotVoiceTranscriber =
    TdScenarioFactory.create<TranscriberBotVoiceTranscriberConfig>("transcriberbot_voice_transcriber.yaml") { config ->
        TranscribeMyVoiceNotesWithTranscriberBot(config.language)
    }