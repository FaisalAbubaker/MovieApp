package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.presentation.navigation.BottomNavigationItem
import com.example.movieapp.presentation.navigation.MovieNavGraph
import com.example.movieapp.presentation.navigation.Screens
import com.example.movieapp.presentation.navigation.popUpToTop
import com.example.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                val navController = rememberNavController()

                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when (navBackStackEntry?.destination?.route){
                    Screens.OnBoarding.route -> false
                    else -> true
                }
                val navigationSelectedItem = rememberSaveable {
                    mutableIntStateOf(0)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                BottomNavigationBar(navigationSelectedItem, navController)
                            }
                        }
                    }
                ) { paddingValues ->
                    //We need to setup our NavHost in here
                    Box(modifier = Modifier.padding(paddingValues)) {
                        MovieNavGraph(navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun RowScope.BottomNavigationBar(
        navigationSelectedItem: MutableIntState,
        navController: NavHostController
    ){
        BottomNavigationItem().bottomNavigationItems()
            .forEachIndexed { index, navigationItem ->
                NavigationBarItem(
                    selected = index == navigationSelectedItem.intValue,
                    label = {
                            Text(navigationItem.label)
                    },
                    icon = {
                        Icon(
                            navigationItem.icon,
                            contentDescription = navigationItem.label
                        )
                    },
                    onClick = {
                        navigationSelectedItem.intValue = index
                        navController.navigate(navigationItem.route){
                            popUpToTop(navController)
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )

                }

            }
    }


