package com.example.movieapp.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.domain.search.SearchMovieUseCase
import com.example.movieapp.model.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
): ViewModel(){

    var searchMovieState: MutableStateFlow<PagingData<Results>> =
        MutableStateFlow(PagingData.empty())


    fun getSearch(query: String){
        viewModelScope.launch {
            searchMovieUseCase.invoke(query).distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    searchMovieState.value = it
                }
        }
    }
}