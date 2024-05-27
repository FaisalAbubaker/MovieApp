package com.example.movieapp.data.repository

import com.example.movieapp.BuildConfig
import com.example.movieapp.Constant
import com.example.movieapp.data.remote.MovieAPI
import com.example.movieapp.model.MovieDetailResponse
import com.example.movieapp.model.SearchResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepositoryTest {
    private lateinit var server: MockWebServer
    private lateinit var api: MovieAPI

    @Before
    fun setUp(){
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/${Constant.MOVIE_BASE_URL}"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MovieAPI::class.java)
    }

    @After
    fun tearDown(){
        server.shutdown()
    }

    @Test
    fun getMovieDetails() = runTest {
        val dto = MovieDetailResponse()
        val gson : Gson = GsonBuilder().create()
        val json = gson.toJson(dto)
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)
        val data = api.getMovieDetails(912413, BuildConfig.TMDB_API_KEY)
        server.takeRequest()
        assertEquals(data.body(),dto)
    }

    @Test
    fun `getMovieDetails, returns Error` () = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        res.setBody("404")
        server.enqueue(res)

        val data = api.getMovieDetails(92359)

        server.takeRequest()

        assertTrue(res.status.contains(data.raw().message,true))
        assertEquals(data.isSuccessful,false)
    }

    @Test
    fun getUpComingMovies() = runTest {
        val dto = SearchResponse()
        val gson : Gson = GsonBuilder().create()
        val json = gson.toJson(dto)
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)
        val data = api.getUpcoming()
        server.takeRequest()
        assertEquals(data.body(),dto)

    }
}

