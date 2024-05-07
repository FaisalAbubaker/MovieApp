package com.example.movieapp.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.details.GetMovieDetailsUseCase
import com.example.movieapp.model.MovieDetailResponse
import com.example.movieapp.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
): ViewModel(){

    var movieDetailsState: MutableStateFlow<UIState<MovieDetailResponse>> =
        MutableStateFlow(UIState.Loading())

    fun getDetails(movieId: Int){
        viewModelScope.launch {
            when (val response = getMovieDetailsUseCase.invoke(movieId)){
                is UIState.Success -> movieDetailsState.value = UIState.Success(response.data)
                is UIState.Error -> movieDetailsState.value = UIState.Error(response.error)
                is UIState.Empty -> movieDetailsState.value = UIState.Empty(title = response.title)
                is UIState.Loading -> movieDetailsState.value = UIState.Loading()
            }
        }
    }

}