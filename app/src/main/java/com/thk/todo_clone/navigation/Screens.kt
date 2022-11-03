package com.thk.todo_clone.navigation

import androidx.navigation.NavHostController
import com.thk.data.util.Constants
import com.thk.todo_clone.util.Action

class Screens(navController: NavHostController) {
    /* 화면 이동에 대한 lambda는 여기에 모아둠 */

    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            // list 화면으로 이동할 때 stack에 쌓여있는 list 화면을 지워서 단 1개만 남도록
            popUpTo(Constants.SCREEN_LIST) { inclusive = true }
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}