package com.example.dailynote.navigation

sealed class Destinations(val route: String) {
    data object Home : Destinations("home_screen")
    data object Add : Destinations("add_screen")
}