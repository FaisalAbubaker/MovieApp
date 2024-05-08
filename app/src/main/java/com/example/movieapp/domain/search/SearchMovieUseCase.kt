package com.example.movieapp.domain.search

import androidx.paging.PagingData
import com.example.movieapp.data.repository.MoviesRepository
import com.example.movieapp.model.Results
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    operator fun invoke(query: String): Flow<PagingData<Results>> {
        return moviesRepository.searchMovies(query)
    }
}