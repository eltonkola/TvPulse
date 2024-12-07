package org.eltonkola.tvpulse.data.remote.service

import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import io.ktor.client.engine.cio.*
import org.eltonkola.tvpulse.data.remote.model.MovieDetails
import org.eltonkola.tvpulse.data.remote.model.TmdbListResponse
import org.eltonkola.tvpulse.data.remote.model.TvShowDetails

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

    val json = Json {
        ignoreUnknownKeys = true // Ignores unknown keys in the JSON
    }

    suspend fun getTrendingTvShows(): TmdbListResponse<TvShowDetails> {
        val endpoint = "$baseUrl/trending/tv/day?language=en-US"
        return try {

            val response: HttpResponse = client.get(endpoint) {
                header(HttpHeaders.Authorization, "Bearer $bearerToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (response.status.isSuccess()) {
                val responseBody = response.bodyAsText()
                Logger.i { "responseBody: $responseBody" }
                json.decodeFromString<TmdbListResponse<TvShowDetails>>(responseBody)
            } else {

                throw Exception("API returned error: ${response.status.value} ${response.status.description}")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Error fetching latest TV shows: ${e.message}")

        }
    }

    suspend fun getTrendingMovies(): TmdbListResponse<MovieDetails> {
        val endpoint = "$baseUrl/trending/movie/day?language=en-US"
        return try {

            val response: HttpResponse = client.get(endpoint) {
                header(HttpHeaders.Authorization, "Bearer $bearerToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (response.status.isSuccess()) {
                val responseBody = response.bodyAsText()
                Logger.i { "responseBody: $responseBody" }
                json.decodeFromString<TmdbListResponse<MovieDetails>>(responseBody)
            } else {

                throw Exception("API returned error: ${response.status.value} ${response.status.description}")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Error fetching latest TV shows: ${e.message}")

        }
    }
}