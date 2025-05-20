package com.apero.composeapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController
import com.apero.composeapp.presentation.home.HomeScreen
import platform.UIKit.UIViewController

@Composable
fun App() {
    HomeScreen()
}

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        App()
    }
}
