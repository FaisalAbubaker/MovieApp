package com.example.movieapp.domain.search

import androidx.paging.PagingData
import com.example.movieapp.data.repository.PopularMoviesRepository
import com.example.movieapp.model.Results
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val popularMoviesRepository: PopularMoviesRepository) {
    operator fun invoke(query: String): Flow<PagingData<Results>> {
        return popularMoviesRepository.searchMovies(query)
    }
}