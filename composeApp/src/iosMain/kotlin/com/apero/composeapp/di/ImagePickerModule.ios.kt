package com.apero.composeapp.di

import com.apero.picker_image.ImagePicker
import com.apero.picker_image.IosImagePicker
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.UIKit.UIViewController

actual val imagePickerModule: Module = module {
    factory<ImagePicker> { (viewController: UIViewController) ->
        IosImagePicker(viewController)
    }
} 