package com.example.movieapp.presentation.screens.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.onBoarding.GetDataFromDataStoreUseCase
import com.example.movieapp.domain.onBoarding.SaveDataInDataStoreUseCase
import com.example.movieapp.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnBoardingScreenViewModel @Inject constructor(
    private val saveDataInDataStore: SaveDataInDataStoreUseCase,
    private val getDataFromDataStore: GetDataFromDataStoreUseCase
) : ViewModel() {

    val onBoardingCompleted = MutableStateFlow(false)
    var startDestination: String = Screens.OnBoarding.route

    init {
        getOnBoardingState()
    }

    private fun getOnBoardingState() {
        viewModelScope.launch {
            onBoardingCompleted.value = getDataFromDataStore().stateIn(viewModelScope).value
            startDestination =
                if (onBoardingCompleted.value) Screens.Home.route else Screens.OnBoarding.route

        }
    }

    fun saveOnBoardingState(showOnBoardingPage: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            saveDataInDataStore(showTipsPage = showOnBoardingPage)
        }
    }
}