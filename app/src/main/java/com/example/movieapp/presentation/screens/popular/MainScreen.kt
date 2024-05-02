package com.example.movieapp.presentation.screens.popular

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.Constant
import com.example.movieapp.model.BackdropSize
import com.example.movieapp.model.SearchResponse
import com.example.movieapp.model.UIState

@Composable
fun MainScreen(navController: NavHostController, popularMovieState: MutableState<UIState<SearchResponse>>) {
//    val viewModel: PopularViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        when (val result = popularMovieState.value) {
            is UIState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    items(result.data?.results.orEmpty()) {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("${Constant.MOVIE_IMAGE_URL}${BackdropSize.w300}/${it.posterPath}")
                                .build(), contentDescription = "",
                            Modifier.padding(10.dp)
                        )
                        Text(
                            text = it.title.orEmpty(),
                            Modifier.padding(start = 10.dp)
                        )

                    }
                }
            }

            is UIState.Empty -> {}
            is UIState.Error -> {}
            is UIState.Loading -> {}
        }
    }
}