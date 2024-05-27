package com.example.movieapp.domain.popular

import com.example.movieapp.data.repository.MoviesRepository
import com.example.movieapp.domain.details.GetMovieDetailsUseCase
import com.example.movieapp.model.MovieDetailResponse
import com.example.movieapp.model.UIState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseTest:BaseTestCase() {

    @MockK
    lateinit var moviesRepository: MoviesRepository

    @MockK
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase


    @Before
    override fun setUp(){
        super.setUp()
        getMovieDetailsUseCase = GetMovieDetailsUseCase(moviesRepository)
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    val dummy = UIState.Success(MovieDetailResponse())

    @Test
    fun invoke(){
        runTest {
            val expected = dummy
            coEvery {
                moviesRepository.getMovieDetails(11)
            } returns expected
            val result = getMovieDetailsUseCase(11)
            Assert.assertEquals(expected, result)
        }
    }
}