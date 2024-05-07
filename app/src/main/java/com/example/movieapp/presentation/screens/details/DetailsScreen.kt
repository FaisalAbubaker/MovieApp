package com.example.movieapp.presentation.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.Constant.MOVIE_IMAGE_URL
import com.example.movieapp.R
import com.example.movieapp.model.BackdropSize
import com.example.movieapp.model.UIState


@Composable
fun MovieDetailsScreen(
    detailsScreenViewModel: DetailsScreenViewModel,
    movieId: Int?
) {

    LaunchedEffect(Unit) {
        if(movieId != null) {
            detailsScreenViewModel.getDetails(movieId)
        }
    }
    val result = detailsScreenViewModel.movieDetailsState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        when (val data = result.value) {
            is UIState.Success -> {
                LazyColumn {
                    item{
                        AsyncImage(
                            model = "${MOVIE_IMAGE_URL}${BackdropSize.w1280}/${data.data?.backdropPath}",
                            contentDescription = "",
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .heightIn(max = 300.dp),
                            contentScale = ContentScale.FillWidth,
                            error = painterResource(id = R.drawable.no_poster),
                            placeholder = painterResource(id = R.drawable.no_poster)
                        )

                    }
                    item {
                        Column {
                            Text(text = data.data?.title.toString())
                            AsyncImage(
                                model = "${MOVIE_IMAGE_URL}${BackdropSize.w300}/${data.data?.posterPath}",
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(10.dp),
                                contentScale = ContentScale.FillWidth,
                                error = painterResource(id = R.drawable.no_poster),
                                placeholder = painterResource(id = R.drawable.no_poster)
                            )
                            Text(text = data.data?.genres?.map { it.name }.toString())

                        }
                    }
                }
            }
                is UIState.Empty -> {}
                is UIState.Error -> {}
                is UIState.Loading -> {}

        }
    }
}