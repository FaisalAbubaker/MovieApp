package com.example.movieapp.domain.details

import com.example.movieapp.data.repository.MoviesRepository
import com.example.movieapp.model.MovieDetailResponse
import com.example.movieapp.model.UIState
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetMovieDetailsUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): UIState<MovieDetailResponse> {
        return moviesRepository.getMovieDetails(movieId = movieId)
    }
}