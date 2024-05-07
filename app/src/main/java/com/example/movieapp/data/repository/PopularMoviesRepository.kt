package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.paging.MoviePagingSource
import com.example.movieapp.data.paging.SearchPagingSource
import com.example.movieapp.data.remote.MovieAPI
import com.example.movieapp.model.MovieDetailResponse
import com.example.movieapp.model.Results
import com.example.movieapp.model.UIState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesRepository @Inject constructor(
    val movieApi: MovieAPI
) {
//    suspend fun getPopularMovies(): UIState<SearchResponse>{
//        try{
//            val response = movieApi.getUpcoming()
//            if(response.isSuccessful && response.body() != null){
//                return UIState.Success(response.body())
//            }else{
//                return UIState.Empty(message = response.message().toString())
//            }
//        }catch (e: Exception){
//            return UIState.Error(e.message.toString())
//        }
//    }
    fun getPopularMovies(): Flow<PagingData<Results>> {
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 2),
            pagingSourceFactory = {
                MoviePagingSource(movieApi)
            }
        ).flow
    }
    fun searchMovies(query: String): Flow<PagingData<Results>>{
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 2),
            pagingSourceFactory = {
                SearchPagingSource(movieApi, query)
            }
        ).flow
    }
        suspend fun getMovieDetails(movieId: Int): UIState<MovieDetailResponse> {
        try{
            val response = movieApi.getMovieDetails(movieId = movieId)
            if(response.isSuccessful && response.body() != null){
                return UIState.Success(response.body())
            }else{
                return UIState.Empty(message = response.message().toString())
            }
        }catch (e: Exception){
            return UIState.Error(e.message.toString())
        }
    }
}