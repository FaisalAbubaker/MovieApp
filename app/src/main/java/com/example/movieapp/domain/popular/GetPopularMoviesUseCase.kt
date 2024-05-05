package com.example.movieapp.domain.popular

import androidx.paging.PagingData
import com.example.movieapp.data.repository.PopularMoviesRepository
import com.example.movieapp.model.Results
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class GetPopularMoviesUseCase @Inject constructor(private val popularMoviesRepository: PopularMoviesRepository) {
    operator fun invoke(): Flow<PagingData<Results>> {
       return popularMoviesRepository.getPopularMovies()
    }
}