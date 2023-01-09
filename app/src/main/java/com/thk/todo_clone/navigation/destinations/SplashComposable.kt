package com.thk.todo_clone.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thk.data.util.Constants
import com.thk.todo_clone.ui.screens.splash.SplashScreen

fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = Constants.SCREEN_SPLASH
    ) {
        SplashScreen(navigateToListScreen)
    }
}