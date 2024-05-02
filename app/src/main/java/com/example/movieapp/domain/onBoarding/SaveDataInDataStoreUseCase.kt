package com.example.movieapp.domain.onBoarding



import com.example.movieapp.data.dataStore.MovieAppDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveDataInDataStoreUseCase @Inject constructor(
    private val dataStore: MovieAppDataStore
) {
    suspend operator fun invoke(showTipsPage: Boolean) {
        dataStore.saveOnBoardingState(showTipsPage = showTipsPage)
    }
}