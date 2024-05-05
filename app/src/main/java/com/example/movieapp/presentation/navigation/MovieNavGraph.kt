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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.presentation.screens.onBoarding.OnBoardingScreen
import com.example.movieapp.presentation.screens.onBoarding.OnBoardingScreenViewModel
import com.example.movieapp.presentation.screens.popular.PopularMovieScreen
import com.example.movieapp.presentation.screens.popular.PopularViewModel

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
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)) {

            }
        }
        composable(Screens.Profile.route) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)) {

            }
        }
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController){
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return){
        inclusive = true
    }
}
