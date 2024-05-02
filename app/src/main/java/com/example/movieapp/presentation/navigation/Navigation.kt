package com.example.movieapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.MainScreen
import com.example.movieapp.presentation.screens.onBoarding.OnBoardingScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.OnBoardingScreen.route){
        composable(route = Screens.OnBoardingScreen.route){
            OnBoardingScreen(navController = navController)
        }
        composable(route = Screens.MainScreen.route){
            MainScreen(navController = navController)
        }
    }
}
