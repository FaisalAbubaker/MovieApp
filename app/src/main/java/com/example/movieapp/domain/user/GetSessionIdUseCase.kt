package com.example.movieapp.domain.user

import com.example.movieapp.data.repository.MoviesRepository
import javax.inject.Inject

class GetSessionIdUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(requestToken: String) = moviesRepository.getSessionId(requestToken)
}