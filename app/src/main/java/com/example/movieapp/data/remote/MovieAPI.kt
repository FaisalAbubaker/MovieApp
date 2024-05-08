package com.example.movieapp.data.remote

import com.example.movieapp.BuildConfig
import com.example.movieapp.model.MovieDetailResponse
import com.example.movieapp.model.SearchResponse
import com.example.movieapp.model.UserAccount
import com.example.movieapp.model.UserTokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI{
    @GET("3/movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key")
        apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("Language")
        language: String = "en-US",
        @Query("page")
        page: Int = 1,

    ): Response<SearchResponse>


    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id")
        movieId: Int,
        @Query("api_key")
        apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("append_to_response")
        append_to_response: String?=""
    ): Response<MovieDetailResponse>

    @GET("3/search/multi")
    suspend fun searchMovie(
        @Query("query")
        query: String,
        @Query("api_key")
        apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("Language")
        language: String = "en-US",
        @Query("page")
        page: Int = 1,

        ): Response<SearchResponse>

    @GET("3/authentication/token/new")
    suspend fun getUserToken(
        @Query("api_key")
        apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<UserTokenResponse>

    @GET("3/authentication/session/new")
    suspend fun getSessionId(
        @Query("api_key")
        apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("request_token")
        requestToken: String
    ): Response<UserTokenResponse>

    @GET("3/account")
    suspend fun getUserAccount(
        @Query("api_key")
        apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("session_id")
        sessionId: String
    ): Response<UserAccount>
}

