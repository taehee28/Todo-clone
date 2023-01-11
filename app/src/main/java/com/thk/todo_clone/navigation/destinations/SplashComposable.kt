@file:OptIn(ExperimentalAnimationApi::class)

package com.thk.todo_clone.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.thk.data.util.Constants
import com.thk.todo_clone.ui.screens.splash.SplashScreen

fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = Constants.SCREEN_SPLASH,
        exitTransition = {
            slideOutVertically(
                animationSpec = tween(300),
                targetOffsetY = { fullHeight -> -fullHeight }
            )
        }
    ) {
        SplashScreen(navigateToListScreen)
    }
}