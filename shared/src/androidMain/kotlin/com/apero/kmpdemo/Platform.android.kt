package com.apero.kmpdemo

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
    override val isAndroid: Boolean = true
}

actual fun getPlatform(): Platform = AndroidPlatform()