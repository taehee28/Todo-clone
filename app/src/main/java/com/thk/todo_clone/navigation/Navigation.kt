package com.thk.todo_clone.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.thk.data.util.Constants
import com.thk.todo_clone.navigation.destinations.listComposable
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
    
    NavHost(
        navController = navController,
        startDestination = Constants.SCREEN_LIST
    ) {
        // extension을 활용하여 navHost 구현 부분은 깔끔하게 유지
        listComposable(navigateToTaskScreen = screen.task, sharedViewModel = sharedViewModel)
        taskComposable(navigateToListScreen = screen.list, sharedViewModel = sharedViewModel)
    }
}