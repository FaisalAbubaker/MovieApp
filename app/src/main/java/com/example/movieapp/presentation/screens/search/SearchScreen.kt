package com.example.movieapp.presentation.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.movieapp.Constant
import com.example.movieapp.R
import com.example.movieapp.model.BackdropSize
import com.example.movieapp.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController,
                       searchScreenViewModel: SearchScreenViewModel
) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(query = query, onQueryChange = {query = it}, onSearch = {active = false
            searchScreenViewModel.getSearch(query)}
            , active = active, onActiveChange = { active = it},
            placeholder = {
                          Text(text = "Search")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            modifier = Modifier.fillMaxWidth()) {
        }

        val moviePagingItems = searchScreenViewModel.searchMovieState.collectAsLazyPagingItems()
        Box{
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement =  Arrangement.Center,
                horizontalArrangement = Arrangement.Center
            ) {
                items(moviePagingItems.itemCount) { index ->
                    if(moviePagingItems[index]?.adult==false){
                        AsyncImage(
                            model = "${Constant.MOVIE_IMAGE_URL}${BackdropSize.w300}/${moviePagingItems[index]?.posterPath}",
                            contentDescription = "",
                            modifier = Modifier
                                .padding(2.dp)
                                .clickable {
                                    navController.navigate("${Screens.Details.route}/${moviePagingItems[index]?.id}")
                                },
                            contentScale = ContentScale.FillWidth,
                            error = painterResource(id = R.drawable.no_poster),
                            placeholder = painterResource(id = R.drawable.no_poster)
                        )
                    }
                }

            }
            moviePagingItems.apply{
                when{
                    loadState.refresh is LoadState.Loading -> {
//                        Row(
//                            Modifier.fillMaxSize(),
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            CircularProgressIndicator()
//
//                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val error = moviePagingItems.loadState.refresh as LoadState.Error
                        Row(
                            Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = error.error.localizedMessage.orEmpty(),
                                modifier = Modifier)
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        Row(
                            Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            CircularProgressIndicator()
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = moviePagingItems.loadState.append as LoadState.Error
                        Text(text = error.error.localizedMessage.orEmpty(),
                            modifier = Modifier)
                    }
                }
            }
        }
    }

}