package com.apero.composeapp.presentation.home

import com.apero.picker_image.ImagePicker

fun createHomeViewModel(imagePicker: ImagePicker): HomeViewModel {
    return HomeViewModel(imagePicker)
} 