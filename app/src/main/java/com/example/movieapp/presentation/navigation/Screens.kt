package com.example.movieapp.presentation.navigation

sealed class Screens(val route: String){
    data object OnBoardingScreen : Screens("onBoardingScreen")
    data object MainScreen : Screens("mainScreen")
}