package com.apero.picker_image

internal object PermissionResultCallback {
    private val callbacks = mutableMapOf<Int, (Boolean) -> Unit>()

    fun addCallback(requestCode: Int, callback: (Boolean) -> Unit) {
        callbacks[requestCode] = callback
    }

    fun invokeCallback(requestCode: Int, granted: Boolean) {
        callbacks.remove(requestCode)?.invoke(granted)
    }
} 