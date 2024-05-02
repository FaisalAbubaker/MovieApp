package com.example.movieapp.domain.onBoarding


import com.example.movieapp.data.dataStore.MovieAppDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDataFromDataStoreUseCase @Inject constructor(
    private val dataStore: MovieAppDataStore
) {
    operator fun invoke() : Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }
}