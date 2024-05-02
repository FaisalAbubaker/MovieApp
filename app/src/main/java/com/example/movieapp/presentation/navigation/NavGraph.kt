package com.example.movieapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.presentation.screens.onBoarding.OnBoardingScreen
import com.example.movieapp.presentation.screens.onBoarding.OnBoardingScreenViewModel
import com.example.movieapp.presentation.screens.popular.MainScreen
import com.example.movieapp.presentation.screens.popular.PopularViewModel

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val onBoardingScreenViewModel: OnBoardingScreenViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = onBoardingScreenViewModel.startDestination){
        composable(route = Screens.OnBoardingScreen.route){
            OnBoardingScreen(onBoardingScreenViewModel, navController)
        }
        composable(route = Screens.MainScreen.route){
            val viewModel = hiltViewModel<PopularViewModel>()
            MainScreen(navController = navController, viewModel.popularMovieState)
        }
    }
}
