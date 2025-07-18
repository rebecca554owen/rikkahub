package me.rerere.tts.provider

import android.content.Context
import kotlinx.coroutines.flow.Flow
import me.rerere.tts.model.AudioChunk
import me.rerere.tts.model.TTSRequest
import me.rerere.tts.model.TTSResponse
import me.rerere.tts.provider.providers.OpenAITTSProvider
import me.rerere.tts.provider.providers.GeminiTTSProvider
import me.rerere.tts.provider.providers.SystemTTSProvider

class TTSManager(private val context: Context) {
  private val openAIProvider = OpenAITTSProvider()
  private val geminiProvider = GeminiTTSProvider()
  private val systemProvider = SystemTTSProvider(context)

  suspend fun generateSpeech(
    providerSetting: TTSProviderSetting,
    request: TTSRequest
  ): TTSResponse {
    return when (providerSetting) {
      is TTSProviderSetting.OpenAI -> openAIProvider.generateSpeech(providerSetting, request)
      is TTSProviderSetting.Gemini -> geminiProvider.generateSpeech(providerSetting, request)
      is TTSProviderSetting.SystemTTS -> systemProvider.generateSpeech(providerSetting, request)
    }
  }

  suspend fun streamSpeech(
    providerSetting: TTSProviderSetting,
    request: TTSRequest
  ): Flow<AudioChunk> {
    return when (providerSetting) {
      is TTSProviderSetting.OpenAI -> openAIProvider.streamSpeech(providerSetting, request)
      is TTSProviderSetting.Gemini -> geminiProvider.streamSpeech(providerSetting, request)
      is TTSProviderSetting.SystemTTS -> systemProvider.streamSpeech(providerSetting, request)
    }
  }

  suspend fun testConnection(providerSetting: TTSProviderSetting): Boolean {
    return when (providerSetting) {
      is TTSProviderSetting.OpenAI -> openAIProvider.testConnection(providerSetting)
      is TTSProviderSetting.Gemini -> geminiProvider.testConnection(providerSetting)
      is TTSProviderSetting.SystemTTS -> systemProvider.testConnection(providerSetting)
    }
  }
}