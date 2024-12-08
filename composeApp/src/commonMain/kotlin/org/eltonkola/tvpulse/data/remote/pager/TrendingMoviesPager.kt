package org.eltonkola.tvpulse.data.remote.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.eltonkola.tvpulse.data.remote.model.TrendingTvShowDetails
import org.eltonkola.tvpulse.data.remote.service.TmdbApiClient

class TrendingTvShowsPager(
    private val apiService: TmdbApiClient
) : PagingSource<Int, TrendingTvShowDetails>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingTvShowDetails> {
        val currentPage = params.key ?: 1
        return try {
            val response = apiService.getTrendingTvShows(page = currentPage, pageSize = params.loadSize)
                 LoadResult.Page(
                    data = response.results,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = (response.page) + 1
                )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TrendingTvShowDetails>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}