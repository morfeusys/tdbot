package com.justai.gramlin.server.scenarios

import com.justai.gramlin.scenario.TranscribeMyVoiceNotesWithTranscriberBot
import com.justai.gramlin.server.ScenarioFactory

data class TranscriberBotVoiceTranscriberConfig(val language: String)

val TranscriberBotVoiceTranscriber =
    ScenarioFactory.create<TranscriberBotVoiceTranscriberConfig>("transcriberbot_voice_transcriber.yaml") { config ->
        TranscribeMyVoiceNotesWithTranscriberBot(config.language)
    }