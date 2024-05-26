package com.example.dailynote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailynote.ui.screens.create.CreateScreen
import com.example.dailynote.ui.screens.home.HomeScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinations.Home.route) {
        composable(
            Destinations.Home.route,
        ) {
            HomeScreen(
                navigateToAdd = { navController.navigate(Destinations.Add.route) }
            )
        }

        composable(
            Destinations.Add.route,
        ) {
            CreateScreen(
                navigateToBack = { navController.popBackStack() }
            )
        }
    }

}