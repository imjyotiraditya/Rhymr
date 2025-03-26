package dev.jyotiraditya.rhymr.data.remote

import dev.jyotiraditya.rhymr.data.model.LyricsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.time.Duration

object LyricsApiService {
    private val client = HttpClient(OkHttp) {
        install(HttpTimeout) {
            requestTimeoutMillis = Duration.ofMinutes(2).toMillis()
            connectTimeoutMillis = Duration.ofSeconds(20).toMillis()
            socketTimeoutMillis = Duration.ofMinutes(1).toMillis()
        }

        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 3)
            retryOnException(maxRetries = 3, retryOnTimeout = true)
            exponentialDelay()
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }

        engine {
            config {
                retryOnConnectionFailure(true)
                connectTimeout(Duration.ofSeconds(20))
                readTimeout(Duration.ofMinutes(1))
                writeTimeout(Duration.ofMinutes(1))
            }
        }
    }

    private const val BASE_URL = "https://lyrichub.vercel.app"

    private suspend fun getLyrics(endpoint: String, query: String): LyricsResponse =
        withContext(Dispatchers.IO) {
            client.get("$BASE_URL/$endpoint") {
                parameter("query", query)
            }.body()
        }

    suspend fun getLyricsFromSpotify(query: String): LyricsResponse =
        getLyrics("/api/spotify", query)

    suspend fun getLyricsFromGenius(query: String): LyricsResponse =
        getLyrics("/api/genius", query)
}