package com.thk.todo_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.thk.todo_clone.navigation.SetupNavigation
import com.thk.todo_clone.ui.theme.TodocloneTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodocloneTheme {
                navController = rememberNavController()
                SetupNavigation(navController = navController)
            }
        }
    }
}