package com.thk.todo_clone.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.thk.data.util.Constants
import com.thk.todo_clone.ui.screens.list.ListScreen
import com.thk.todo_clone.ui.viewmodel.SharedViewModel
import com.thk.todo_clone.util.toAction

/**
 * list 화면에 대한 navGraph의 composable 호출을 해당 화면 파일에 모아둠
 *
 * @param navigateToTaskScreen list 화면에서 task 화면으로 이동할 수 있게 해주는 lambda
 */
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = Constants.SCREEN_LIST,
        arguments = listOf(navArgument(Constants.ARG_KEY_LIST) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(Constants.ARG_KEY_LIST).toAction()

        LaunchedEffect(key1 = action) {
            sharedViewModel.handleDatabaseActions(action)
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}