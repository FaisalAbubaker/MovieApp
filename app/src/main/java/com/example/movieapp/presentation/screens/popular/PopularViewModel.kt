package com.example.movieapp.presentation.screens.popular

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.popular.GetPopularMoviesUseCase
import com.example.movieapp.model.SearchResponse
import com.example.movieapp.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
): ViewModel(){

    var popularMovieState: MutableState<UIState<SearchResponse>> = mutableStateOf(UIState.Loading())

    init{
        getArtWorks()
    }

    private fun getArtWorks(){
        viewModelScope.launch {
            when (val response = getPopularMoviesUseCase.invoke()){
                is UIState.Success -> popularMovieState.value = UIState.Success(response.data)
                is UIState.Error -> popularMovieState.value = UIState.Error(response.error)
                is UIState.Empty -> popularMovieState.value = UIState.Empty(title = response.title)
                is UIState.Loading -> popularMovieState.value = UIState.Loading()
            }
        }
    }

}