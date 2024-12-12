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
import org.eltonkola.tvpulse.data.remote.model.*

class TmdbApiClient {

    private val bearerToken: String =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZmRhZGUzZGQ2YmQ1MGY5OTRjOGNlMWNhNzRmYzExNyIsIm5iZiI6MTUxMjc4ODY3Ny4yMTcsInN1YiI6IjVhMmI1MmM1YzNhMzY4MGI4ODEzNjdhMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.TGEWqGILkCmQXeAKAxjZfjItfx8EGcufCiEIRbLyjkU"

    private val client = HttpClient(CIO) {
        engine {
            maxConnectionsCount = 1000
            endpoint {
                maxConnectionsPerRoute = 100
                pipelineMaxSize = 20
                keepAliveTime = 5000
                connectTimeout = 5000
                connectAttempts = 5
            }
        }
    }


    private val baseUrl = "https://api.themoviedb.org/3"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private suspend inline fun <reified T> fetchFromApi(endpoint: String): T {
        return try {
            val response: HttpResponse = client.get(endpoint) {
                header(HttpHeaders.Authorization, "Bearer $bearerToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (response.status.isSuccess()) {
                val responseBody = response.bodyAsText()
                Logger.i { "endpoint: $endpoint" }
                Logger.i { "responseBody: $responseBody" }
                json.decodeFromString(responseBody)
            } else {
                throw Exception("API returned error: ${response.status.value} ${response.status.description}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Error during API call: ${e.message}")
        }
    }


    suspend fun getTrendingTvShows(page: Int, pageSize: Int): TmdbListResponse<TrendingTvShowDetails> {
        val endpoint = "$baseUrl/trending/tv/day?language=en-US&page=$page"//&pageSize=$pageSize
        Logger.i( "endpoint: $endpoint" )
        return fetchFromApi(endpoint)
    }

    suspend fun getTrendingMovies(page: Int, pageSize: Int): TmdbListResponse<TrendingMovieDetails> {
        val endpoint = "$baseUrl/trending/movie/day?language=en-US&page=$page"//&pageSize=$pageSize
        return fetchFromApi(endpoint)
    }

    suspend fun getMovieDetails(id: Int): MovieDetails {
        val endpoint = "$baseUrl/movie/$id"
        return fetchFromApi(endpoint)
    }

    suspend fun getTvShowDetails(id: Int): TvShowDetails {
        val endpoint = "$baseUrl/tv/$id"
        return fetchFromApi(endpoint)
    }

    suspend fun getMovieTrailers(id: Int): MovieVideosResponse {
        val endpoint = "$baseUrl/movie/$id/videos"
        return fetchFromApi(endpoint)
    }
    suspend fun getMovieCredits(id: Int): MovieCreditsResponse {
        val endpoint = "$baseUrl/movie/$id/credits"
        return fetchFromApi(endpoint)
    }

    suspend fun getSimilarMovies(id: Int): TmdbListResponse<TrendingMovieDetails> {
        val endpoint = "$baseUrl/movie/$id/similar"
        return fetchFromApi(endpoint)
    }

    suspend fun getPerson(id: Int): Person {
        val endpoint = "$baseUrl/person/$id"
        return fetchFromApi(endpoint)
    }

    suspend fun getActorMovies(id: Int): PersonCreditsResponse {
        val endpoint = "$baseUrl/person/$id/credits"
        return fetchFromApi(endpoint)
    }

    suspend fun getActorTvShows(id: Int): PersonCreditsResponse {
        val endpoint = "$baseUrl/person/$id/tv_credits"
        return fetchFromApi(endpoint)
    }

}