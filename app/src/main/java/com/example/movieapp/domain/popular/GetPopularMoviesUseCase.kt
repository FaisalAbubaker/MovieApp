package com.example.movieapp.domain.popular

import com.example.movieapp.data.repository.PopularMoviesRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetPopularMoviesUseCase @Inject constructor(private val popularMoviesRepository: PopularMoviesRepository) {
    suspend operator fun invoke() = popularMoviesRepository.getPopularMovies()
}