package com.apero.composeapp.presentation.home

import androidx.compose.runtime.Composable
import com.apero.picker_image.ImagePicker

@Composable
actual fun HomeScreen(imagePicker: ImagePicker) {
    val viewModel = createHomeViewModel(imagePicker)
    HomeScreenContent(viewModel = viewModel)
} 