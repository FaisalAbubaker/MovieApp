package com.example.movieapp.domain.user

import com.example.movieapp.data.repository.MoviesRepository
import javax.inject.Inject

class GetUserAccountUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(sessionId: String) = moviesRepository.getUserAccount(sessionId)
}