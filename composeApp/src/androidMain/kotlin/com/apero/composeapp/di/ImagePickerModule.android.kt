package com.apero.composeapp.di

import com.apero.picker_image.AndroidImagePicker
import com.apero.picker_image.ImagePicker
import org.koin.core.module.Module
import org.koin.dsl.module

actual val imagePickerModule: Module = module {
    factory<ImagePicker> { (activity: androidx.activity.ComponentActivity) ->
        AndroidImagePicker(activity).apply { setup() }
    }
} 