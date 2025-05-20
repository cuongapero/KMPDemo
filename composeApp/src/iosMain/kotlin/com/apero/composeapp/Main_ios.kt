package com.apero.composeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.uikit.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.apero.composeapp.presentation.home.HomeScreen
import com.apero.picker_image.IosImagePicker
import platform.UIKit.UIViewController

@Composable
fun App() {
    val viewController = LocalUIViewController.current
    val imagePicker = remember { IosImagePicker(viewController) }
    HomeScreen(imagePicker)
}

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
