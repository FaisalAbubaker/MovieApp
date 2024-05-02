package com.example.movieapp.presentation.navigation

sealed class Screens(val route: String){
    object OnBoardingScreen : Screens("onBoardingScreen")
    object MainScreen : Screens("mainScreen")
}