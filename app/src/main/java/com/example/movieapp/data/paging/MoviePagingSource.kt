package com.example.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.BuildConfig
import com.example.movieapp.data.dao.MovieDao
import com.example.movieapp.data.remote.MovieAPI
import com.example.movieapp.model.Movie
import com.example.movieapp.model.Results
import okio.IOException

class MoviePagingSource(
    private val movieAPI: MovieAPI,
    private val movieDao: MovieDao
): PagingSource<Int, Results>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results>{
        val currentPage = params.key?: 1
        return try{
            val cachedMovies = movieDao.getCachedMovies()
            try {
                val movies = movieAPI.getUpcoming(
                    apiKey = BuildConfig.TMDB_API_KEY,
                    page = currentPage
                )
                val moviesList = movies.body()?.results?.map {
                    Movie(
                        id = it.id ?: -1,
                        title = it.title.orEmpty(),
                        backdropPath = it.backdropPath.orEmpty(),
                        posterPath = it.posterPath.orEmpty(),
                        page = currentPage
                    )
                }
                movieDao.insertMovies(moviesList.orEmpty())

                val pagesToKeep = listOf(currentPage -1, currentPage, currentPage + 1)
                movieDao.deleteMoviesNotInPages(pagesToKeep)

                LoadResult.Page(
                    data = movies.body()?.results.orEmpty(),
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (movies.body()?.results?.isEmpty() == true) null else movies.body()?.page!! + 1
                )
            } catch (e: Exception){
                LoadResult.Page(
                    data = cachedMovies.map {
                        Results(
                            false,
                            backdropPath = it.backdropPath,
                            id = it.id,
                            title = it.title,
                            posterPath = it.posterPath
                        )
                    },
                    prevKey = null,
                    nextKey = null
                )
            }
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
