package org.eltonkola.tvpulse.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.remote.model.TrendingMovieDetails
import org.eltonkola.tvpulse.data.remote.model.TrendingTvShowDetails
import org.eltonkola.tvpulse.data.remote.pager.TrendingMoviesPager
import org.eltonkola.tvpulse.data.remote.pager.TrendingTvShowsPager
import org.eltonkola.tvpulse.data.remote.service.TmdbApiClient

class TrendingRepository(
    private val apiService: TmdbApiClient = DiGraph.tmdbApiClient
) {

    fun getTrendingTvShows(): Flow<PagingData<TrendingTvShowDetails>> =
        Pager(
            PagingConfig(
                pageSize = 20
            )) {
            TrendingTvShowsPager(apiService)
        }.flow

    fun getTrendingMovies(): Flow<PagingData<TrendingMovieDetails>> =
        Pager(
            PagingConfig(
                pageSize = 20
            )) {
            TrendingMoviesPager(apiService)
        }.flow

}