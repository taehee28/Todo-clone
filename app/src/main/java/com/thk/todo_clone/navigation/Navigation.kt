@file:OptIn(ExperimentalAnimationApi::class)

package com.thk.todo_clone.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.thk.data.util.Constants
import com.thk.todo_clone.navigation.destinations.listComposable
import com.thk.todo_clone.navigation.destinations.splashComposable
import com.thk.todo_clone.navigation.destinations.taskComposable
import com.thk.todo_clone.ui.viewmodel.SharedViewModel

@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    // navController가 변경되면 다시 Screens를 생성해서 저장함
    val screen = remember(navController) {
        Screens(navController)
    }
    
    AnimatedNavHost(
        navController = navController,
        startDestination = Constants.SCREEN_SPLASH
    ) {
        // extension을 활용하여 navHost 구현 부분은 깔끔하게 유지
        splashComposable(navigateToListScreen = screen.splash)
        listComposable(navigateToTaskScreen = screen.list, sharedViewModel = sharedViewModel)
        taskComposable(navigateToListScreen = screen.task, sharedViewModel = sharedViewModel)
    }
}