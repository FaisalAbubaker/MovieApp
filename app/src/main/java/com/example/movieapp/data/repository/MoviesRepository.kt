package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.dao.MovieDao
import com.example.movieapp.data.paging.MoviePagingSource
import com.example.movieapp.data.paging.SearchPagingSource
import com.example.movieapp.data.remote.MovieAPI
import com.example.movieapp.model.MovieDetailResponse
import com.example.movieapp.model.Results
import com.example.movieapp.model.UIState
import com.example.movieapp.model.UserAccount
import com.example.movieapp.model.UserTokenResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    val movieApi: MovieAPI,
    val movieDao: MovieDao
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
                MoviePagingSource(movieApi, movieDao)
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

    suspend fun getUserToken(): UIState<UserTokenResponse>{
        try {
            val response = movieApi.getUserToken()
            if(response.isSuccessful && response.body() != null){
                return UIState.Success(response.body())
            } else {
                return UIState.Empty(message = response.message().toString())
            }
        } catch (e:Exception){
            return UIState.Error(e.message.toString())
        }
    }

    suspend fun getSessionId(requestToken: String): UIState<UserTokenResponse>{
        try {
            val response = movieApi.getSessionId(requestToken = requestToken)
            if(response.isSuccessful && response.body() != null){
                return UIState.Success(response.body())
            } else {
                return UIState.Empty(message = response.message().toString())
            }
        } catch (e:Exception){
            return UIState.Error(e.message.toString())
        }
    }

    suspend fun getUserAccount(sessionId: String): UIState<UserAccount>{
        try {
            val response = movieApi.getUserAccount(sessionId = sessionId)
            if(response.isSuccessful && response.body() != null){
                return UIState.Success(response.body())
            } else {
                return UIState.Empty(message = response.message().toString())
            }
        } catch (e:Exception){
            return UIState.Error(e.message.toString())
        }
    }
}