package com.example.movieapp.domain.details

import com.example.movieapp.data.repository.PopularMoviesRepository
import com.example.movieapp.model.MovieDetailResponse
import com.example.movieapp.model.UIState
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetMovieDetailsUseCase @Inject constructor(private val popularMoviesRepository: PopularMoviesRepository) {
    suspend operator fun invoke(movieId: Int): UIState<MovieDetailResponse> {
        return popularMoviesRepository.getMovieDetails(movieId = movieId)
    }
}