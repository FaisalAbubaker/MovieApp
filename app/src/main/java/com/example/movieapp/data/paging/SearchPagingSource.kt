package com.example.movieapp.data.paging

import androidx.datastore.core.IOException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.BuildConfig
import com.example.movieapp.data.remote.MovieAPI
import com.example.movieapp.model.Results

class SearchPagingSource(private val movieAPI: MovieAPI,
    private val query: String): PagingSource<Int, Results>()  {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results>{
        return try{
            val currentPage = params.key?: 1
            val movies = movieAPI.searchMovie(
                apiKey = BuildConfig.TMDB_API_KEY,
                query = query,
                page = currentPage
            )
            LoadResult.Page(
                data = movies.body()?.results.orEmpty(),
                prevKey = if (currentPage == 1) null else currentPage -1,
                nextKey = if (movies.body()?.results?.isEmpty() == true) null else movies.body()?.page!! + 1
            )
        } catch (exception: IOException){
            return LoadResult.Error(exception)
        } catch (exception: Exception){
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }

}
