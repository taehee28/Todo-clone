@file:OptIn(ExperimentalAnimationApi::class)

package com.thk.todo_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.thk.todo_clone.navigation.SetupNavigation
import com.thk.todo_clone.ui.theme.TodocloneTheme
import com.thk.todo_clone.ui.viewmodel.SharedViewModel
import com.thk.todo_clone.ui.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /*private val viewModel: SharedViewModel by viewModels()*/
    private val toDoViewModel: ToDoViewModel by viewModels()
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TodocloneTheme {
                navController = rememberAnimatedNavController()
                SetupNavigation(
                    navController = navController,
                    toDoViewModel = toDoViewModel
                )
            }
        }
    }
}