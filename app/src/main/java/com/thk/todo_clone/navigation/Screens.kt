package com.thk.todo_clone.navigation

import androidx.navigation.NavHostController
import com.thk.data.util.Constants
import com.thk.todo_clone.model.Action

class Screens(navController: NavHostController) {
    /* 화면 이동에 대한 lambda는 여기에 모아둠 */

    val splash: () -> Unit = {
        navController.navigate(route = "list") {
            // list 화면으로 이동했을 때 뒤에 splash 화면이 남아있으면 안됨
            popUpTo(Constants.SCREEN_SPLASH) { inclusive = true }
       }
    }

    val list: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }

    val task: () -> Unit = {
        navController.navigate("list") {
            // list 화면으로 이동할 때 stack에 쌓여있는 list 화면을 지워서 단 1개만 남도록
            popUpTo(Constants.SCREEN_LIST) { inclusive = true }
        }
    }
}