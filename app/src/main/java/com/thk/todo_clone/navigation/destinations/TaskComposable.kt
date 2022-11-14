package com.thk.todo_clone.navigation.destinations

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.thk.data.util.Constants
import com.thk.todo_clone.util.Action

/**
 * task 화면에 대한 navGraph의 composable 호출을 해당 화면 파일에 모아둠
 *
 * @param navigateToListScreen task 화면에서 list 화면으로 이동할 수 있게 해주는 lambda
 */
fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = Constants.SCREEN_TASK,
        arguments = listOf(navArgument(Constants.ARG_KEY_TASK) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(Constants.ARG_KEY_TASK)
        Log.d("taskComposable", "taskComposable: taskId = $taskId")
    }
}