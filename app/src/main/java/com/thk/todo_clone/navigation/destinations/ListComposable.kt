@file:OptIn(ExperimentalAnimationApi::class)

package com.thk.todo_clone.navigation.destinations

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.thk.data.util.Constants
import com.thk.todo_clone.ui.screens.list.ListScreen
import com.thk.todo_clone.ui.viewmodel.SharedViewModel
import com.thk.todo_clone.ui.viewmodel.ToDoViewModel
import com.thk.todo_clone.util.Action
import com.thk.todo_clone.util.toAction

/**
 * list 화면에 대한 navGraph의 composable 호출을 해당 화면 파일에 모아둠
 *
 * @param navigateToTaskScreen list 화면에서 task 화면으로 이동할 수 있게 해주는 lambda
 */
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit,
    toDoViewModel: ToDoViewModel
) {
    composable(
        route = Constants.SCREEN_LIST,
        arguments = listOf(navArgument(Constants.ARG_KEY_LIST) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        ListScreen(navigateToTaskScreen = navigateToTaskScreen, toDoViewModel = toDoViewModel)
    }
}