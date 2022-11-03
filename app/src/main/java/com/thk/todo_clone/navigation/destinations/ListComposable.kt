package com.thk.todo_clone.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.thk.data.util.Constants

/**
 * list 화면에 대한 navGraph의 composable 호출을 해당 화면 파일에 모아둠
 *
 * @param navigateToTaskScreen list 화면에서 task 화면으로 이동할 수 있게 해주는 lambda
 */
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit
) {
    composable(
        route = Constants.SCREEN_LIST,
        arguments = listOf(navArgument(Constants.ARG_KEY_LIST) {
            type = NavType.StringType
        })
    ) {

    }
}