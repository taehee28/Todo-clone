@file:OptIn(ExperimentalAnimationApi::class)

package com.thk.todo_clone.navigation.destinations

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.thk.data.util.Constants
import com.thk.todo_clone.ui.screens.task.TaskScreen
import com.thk.todo_clone.ui.viewmodel.SharedViewModel
import com.thk.todo_clone.ui.viewmodel.ToDoViewModel
import com.thk.todo_clone.util.Action
import com.thk.todo_clone.util.UIEvent
import com.thk.todo_clone.util.logd

/**
 * task 화면에 대한 navGraph의 composable 호출을 해당 화면 파일에 모아둠
 *
 * @param navigateToListScreen task 화면에서 list 화면으로 이동할 수 있게 해주는 lambda
 */
fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    toDoViewModel: ToDoViewModel
) {
    composable(
        route = Constants.SCREEN_TASK,
        arguments = listOf(navArgument(Constants.ARG_KEY_TASK) {
            type = NavType.IntType
        }),
        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(300),
                initialOffsetX = { fullWidth -> -fullWidth }
            )
        }
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(Constants.ARG_KEY_TASK)

        LaunchedEffect(key1 = taskId) {
            toDoViewModel.onEvent(UIEvent.SelectTask(taskId))
        }

        TaskScreen(toDoViewModel = toDoViewModel, navigateToListScreen = navigateToListScreen)
    }
}