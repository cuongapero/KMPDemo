package com.apero.kmpdemo

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val isAndroid: Boolean = false
}

actual fun getPlatform(): Platform = IOSPlatform()