package org.eltonkola.tvpulse.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.eltonkola.tvpulse.model.TrendingTvShowsResponse

class TvDbApiService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    // Note: Replace with actual TVDB API endpoint and your API key
    private val apiKey = "YOUR_TVDB_API_KEY"
    private val baseUrl = "https://api.themoviedb.org/3"

    suspend fun getTrendingTvShows(page: Int = 1): TrendingTvShowsResponse {
        return client.get("$baseUrl/trending/tv/week") {
            url {
                parameters.append("api_key", apiKey)
                parameters.append("page", page.toString())
            }
        }.body()
    }

    fun close() {
        client.close()
    }
}
