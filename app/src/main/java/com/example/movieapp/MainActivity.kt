package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.Constant.MOVIE_IMAGE_URL
import com.example.movieapp.model.BackdropSize
import com.example.movieapp.model.UIState
import com.example.movieapp.presentation.navigation.Navigation
import com.example.movieapp.presentation.screens.popular.PopularViewModel
import com.example.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val viewModel by viewModels<PopularViewModel>()
        setContent {
            MovieAppTheme {
                Navigation()
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.primaryContainer)
//                ) {
//                    Row {
//                        Image(painter = painterResource(id = R.drawable.film_rolls_amico), contentDescription = "")
//                        Image(painter = painterResource(id = R.drawable.background), contentDescription = "")
//                    }
//                    when (val result = viewModel.popularMovieState.value) {
//                        is UIState.Success -> {
//                            LazyColumn(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .background(MaterialTheme.colorScheme.primaryContainer)
//                            ){
//                                items(result.data?.results.orEmpty()) {
//                                    Text(
//                                        text = it.title.orEmpty(),
//                                        modifier = Modifier.padding(12.dp)
//                                    )
//                                    AsyncImage(
//                                        model = ImageRequest.Builder(LocalContext.current)
//                                            .data("${MOVIE_IMAGE_URL}${BackdropSize.w300}/${it.posterPath}")
//                                            .build(), contentDescription = ""
//                                    )
//
//                                }
//                            }
//                        }
//
//                        is UIState.Empty -> {}
//                        is UIState.Error -> {}
//                        is UIState.Loading -> {}
//                    }
//                }
                //OnBoardingScreen()
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    val viewModel: PopularViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.film_rolls_amico),
                contentDescription = ""
            )
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = ""
            )
        }
        when (val result = viewModel.popularMovieState.value) {
            is UIState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    items(result.data?.results.orEmpty()) {
                        Text(
                            text = it.title.orEmpty(),
                            modifier = Modifier.padding(12.dp)
                        )
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("${MOVIE_IMAGE_URL}${BackdropSize.w300}/${it.posterPath}")
                                .build(), contentDescription = ""
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


