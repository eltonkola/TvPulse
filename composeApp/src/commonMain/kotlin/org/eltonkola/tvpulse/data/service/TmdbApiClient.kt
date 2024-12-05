package org.eltonkola.tvpulse.data.service

import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.call.* // Import to use response.body<T>()
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import org.eltonkola.tvpulse.data.model.TvShowResponse
import io.ktor.client.engine.cio.*

class TmdbApiClient() {

    private val bearerToken: String =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZmRhZGUzZGQ2YmQ1MGY5OTRjOGNlMWNhNzRmYzExNyIsIm5iZiI6MTUxMjc4ODY3Ny4yMTcsInN1YiI6IjVhMmI1MmM1YzNhMzY4MGI4ODEzNjdhMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.TGEWqGILkCmQXeAKAxjZfjItfx8EGcufCiEIRbLyjkU"
 //   private val client = HttpClient(CIO)

    val client = HttpClient(CIO) {
        engine {
            // this: CIOEngineConfig
            maxConnectionsCount = 1000
            endpoint {
                // this: EndpointConfig
                maxConnectionsPerRoute = 100
                pipelineMaxSize = 20
                keepAliveTime = 5000
                connectTimeout = 5000
                connectAttempts = 5
            }
        }
    }


    private val baseUrl = "https://api.themoviedb.org/3"

        suspend fun getTrendingTvShows(): TvShowResponse {
        val endpoint = "$baseUrl/trending/tv/day?language=en-US"
        return try {

            val response: HttpResponse = client.get(endpoint) {
                header(HttpHeaders.Authorization, "Bearer $bearerToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (response.status.isSuccess()) {
                val responseBody = response.bodyAsText()
                Logger.i { "responseBody: $responseBody" }
                Json.decodeFromString<TvShowResponse>(responseBody)
            } else {

                throw Exception("API returned error: ${response.status.value} ${response.status.description}")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Error fetching latest TV shows: ${e.message}")

        }
    }
}