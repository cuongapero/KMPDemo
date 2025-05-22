package com.apero.composeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.uikit.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.apero.composeapp.presentation.home.HomeScreen
import com.apero.picker_image.ImagePicker
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import platform.UIKit.UIViewController

@Composable
fun App() {
    val viewController = LocalUIViewController.current
    val appComponent = remember { AppComponent(viewController) }
    HomeScreen(appComponent.imagePicker)
}

class AppComponent(private val viewController: UIViewController) : KoinComponent {
    val imagePicker: ImagePicker by inject { parametersOf(viewController) }
}

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
