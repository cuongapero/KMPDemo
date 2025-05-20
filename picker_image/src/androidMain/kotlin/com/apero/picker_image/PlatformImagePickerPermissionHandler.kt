package com.apero.picker_image

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual class PlatformImagePickerPermissionHandler : ImagePickerPermissionHandler {
    private var activity: Activity? = null
    private val PERMISSION_REQUEST_CODE_GALLERY = 1001
    private val PERMISSION_REQUEST_CODE_CAMERA = 1002

    fun initialize(activity: Activity) {
        this.activity = activity
    }

    actual override fun hasGalleryPermission(): Boolean {
        val currentActivity = activity ?: return false
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                currentActivity,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                currentActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    actual override fun hasCameraPermission(): Boolean {
        val currentActivity = activity ?: return false
        return ContextCompat.checkSelfPermission(
            currentActivity,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    actual override suspend fun requestGalleryPermission(): Boolean = suspendCancellableCoroutine { continuation ->
        val currentActivity = activity
        if (currentActivity == null) {
            continuation.resume(false)
            return@suspendCancellableCoroutine
        }

        if (hasGalleryPermission()) {
            continuation.resume(true)
            return@suspendCancellableCoroutine
        }

        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        ActivityCompat.requestPermissions(
            currentActivity,
            arrayOf(permission),
            PERMISSION_REQUEST_CODE_GALLERY
        )

        // The result will be handled in onRequestPermissionsResult
        PermissionResultCallback.addCallback(PERMISSION_REQUEST_CODE_GALLERY) { granted ->
            continuation.resume(granted)
        }
    }

    actual override suspend fun requestCameraPermission(): Boolean = suspendCancellableCoroutine { continuation ->
        val currentActivity = activity
        if (currentActivity == null) {
            continuation.resume(false)
            return@suspendCancellableCoroutine
        }

        if (hasCameraPermission()) {
            continuation.resume(true)
            return@suspendCancellableCoroutine
        }

        ActivityCompat.requestPermissions(
            currentActivity,
            arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE_CAMERA
        )

        // The result will be handled in onRequestPermissionsResult
        PermissionResultCallback.addCallback(PERMISSION_REQUEST_CODE_CAMERA) { granted ->
            continuation.resume(granted)
        }
    }

    companion object {
        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            val granted = grantResults.isNotEmpty() && 
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            PermissionResultCallback.invokeCallback(requestCode, granted)
        }
    }
} 