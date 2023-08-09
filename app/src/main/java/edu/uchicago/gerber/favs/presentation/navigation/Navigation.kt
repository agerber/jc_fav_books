package edu.uchicago.gerber.favs.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import edu.uchicago.gerber.favs.authorization.AmplifyService
import edu.uchicago.gerber.favs.presentation.screens.auth.LoginScreen
import edu.uchicago.gerber.favs.presentation.screens.auth.SignUpScreen
import edu.uchicago.gerber.favs.presentation.screens.auth.VerifyScreen
import edu.uchicago.gerber.favs.presentation.screens.contact.ContactScreen
import edu.uchicago.gerber.favs.presentation.screens.details.DetailsScreen
import edu.uchicago.gerber.favs.presentation.screens.favorites.FavoritesScreen
import edu.uchicago.gerber.favs.presentation.screens.search.SearchScreen
import edu.uchicago.gerber.favs.presentation.viewmodels.BookViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    bookViewModel: BookViewModel = viewModel(),
    amplifyService: AmplifyService
) {

    AnimatedNavHost(navController, startDestination = Screen.Login.route) {
        composable(Screen.Search.route) {
            SearchScreen(bookViewModel, navController, amplifyService)

        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(navController, amplifyService)
        }

        composable(Screen.Contact.route) {
            ContactScreen(navController, amplifyService)
        }

        composable(Screen.Detail.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
            },) {
            DetailsScreen(bookViewModel, navController)
        }
    }
}