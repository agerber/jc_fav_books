package edu.uchicago.gerber.favs.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.uchicago.gerber.favs.presentation.screens.contact.ContactScreen
import edu.uchicago.gerber.favs.presentation.screens.details.DetailsScreen
import edu.uchicago.gerber.favs.presentation.screens.favorites.FavoritesScreen
import edu.uchicago.gerber.favs.presentation.screens.search.SearchScreen

@Composable
fun Navigation(
    navController: NavHostController
) {

    NavHost(navController, startDestination = Screen.Search.route) {
        composable(Screen.Search.route) {
            SearchScreen()

        }
        composable(Screen.Favorites.route) {
            FavoritesScreen()
        }

        composable(Screen.Contact.route) {
            ContactScreen()
        }

        composable(Screen.Detail.route) {
            DetailsScreen()
        }
    }
}