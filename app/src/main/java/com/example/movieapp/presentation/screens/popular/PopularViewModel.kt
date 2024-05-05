package com.example.movieapp.presentation.screens.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.domain.popular.GetPopularMoviesUseCase
import com.example.movieapp.model.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
): ViewModel(){

    var popularMovieState: MutableStateFlow<PagingData<Results>> =
        MutableStateFlow(PagingData.empty())

    init{
        getArtWorks()
    }

    private fun getArtWorks(){
        viewModelScope.launch {
            getPopularMoviesUseCase.invoke().distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    popularMovieState.value = it
            }
        }
    }
}