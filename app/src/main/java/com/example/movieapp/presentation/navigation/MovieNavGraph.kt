package com.example.movieapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.presentation.screens.details.DetailsScreenViewModel
import com.example.movieapp.presentation.screens.details.MovieDetailsScreen
import com.example.movieapp.presentation.screens.onBoarding.OnBoardingScreen
import com.example.movieapp.presentation.screens.onBoarding.OnBoardingScreenViewModel
import com.example.movieapp.presentation.screens.popular.PopularMovieScreen
import com.example.movieapp.presentation.screens.popular.PopularViewModel
import com.example.movieapp.presentation.screens.search.SearchScreen
import com.example.movieapp.presentation.screens.search.SearchScreenViewModel

@Composable
fun MovieNavGraph(navController: NavHostController = rememberNavController()) {
    val onBoardingScreenViewModel: OnBoardingScreenViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = onBoardingScreenViewModel.startDestination){
        composable(route = Screens.OnBoarding.route){
            OnBoardingScreen(onBoardingScreenViewModel, navController)
        }
        composable(route = Screens.Home.route){
            val viewModel = hiltViewModel<PopularViewModel>()
            PopularMovieScreen(navController = navController, viewModel.popularMovieState)
        }
        composable(Screens.Search.route) {
            val viewModel = hiltViewModel<SearchScreenViewModel>()
            SearchScreen(navController = navController, searchScreenViewModel = viewModel)
        }
        composable(Screens.Profile.route) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)) {

            }
        }
        composable(
            route = "${Screens.Details.route}/{movie_id}",
            arguments = listOf(navArgument("movie_id") { type = NavType.IntType })
        ) {
            val detailsViewModel = hiltViewModel<DetailsScreenViewModel>()
            MovieDetailsScreen(detailsScreenViewModel = detailsViewModel,
                it.arguments?.getInt("movie_id"))
        }
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController){
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return){
        inclusive = true
        saveState = true
    }
}
