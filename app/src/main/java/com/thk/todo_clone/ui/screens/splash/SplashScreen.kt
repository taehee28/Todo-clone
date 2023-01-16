package com.thk.todo_clone.ui.screens.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.window.SplashScreen
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thk.todo_clone.R
import com.thk.todo_clone.ui.theme.TodocloneTheme
import com.thk.todo_clone.ui.theme.splashScreenBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToListScreen: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else (-100).dp,
        animationSpec = tween(durationMillis = 1000)
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(key1 = Unit) {
        startAnimation = true
        delay(3000)
        navigateToListScreen()
    }

    Splash(offsetState = offsetState, alphaState = alphaState)
}

@Composable
fun Splash(
    offsetState: Dp,
    alphaState: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.splashScreenBackground),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_check_circle),
            contentDescription = stringResource(id = R.string.to_do_logo),
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(100.dp)
                .offset(y = offsetState)
                .alpha(alpha = alphaState)
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Splash(0.dp, 1f)
}

@Preview
@Composable
fun SplashScreenPreview2() {
    TodocloneTheme(darkTheme = true) {
        Splash(0.dp, 1f)
    }
}