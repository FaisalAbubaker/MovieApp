package com.example.movieapp.data.repository

import com.example.movieapp.data.remote.MovieAPI
import com.example.movieapp.model.SearchResponse
import com.example.movieapp.model.UIState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesRepository @Inject constructor(
    val movieApi: MovieAPI
) {
    suspend fun getPopularMovies(): UIState<SearchResponse>{
        try{
            val response = movieApi.getUpcoming()
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