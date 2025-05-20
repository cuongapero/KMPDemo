package com.apero.picker_image

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Android implementation of permission handler
 */
class AndroidPermissionHandler(
    private val activity: ComponentActivity
) : PermissionHandler {
    private var permissionCallback: ((Boolean) -> Unit)? = null
    
    private val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionCallback?.let { callback ->
            callback(isGranted)
            permissionCallback = null
        }
    }

    override suspend fun checkPermission(): PermissionState {
        // For Android 13 and above, we need READ_MEDIA_IMAGES
        // For older versions, we need READ_EXTERNAL_STORAGE
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        return when {
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED -> {
                PermissionState.GRANTED
            }
            activity.shouldShowRequestPermissionRationale(permission) -> {
                PermissionState.DENIED
            }
            else -> {
                PermissionState.PERMANENTLY_DENIED
            }
        }
    }

    override suspend fun requestPermission(): PermissionState = suspendCancellableCoroutine { continuation ->
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        permissionCallback = { isGranted ->
            val state = if (isGranted) {
                PermissionState.GRANTED
            } else if (activity.shouldShowRequestPermissionRationale(permission)) {
                PermissionState.DENIED
            } else {
                PermissionState.PERMANENTLY_DENIED
            }
            continuation.resume(state)
        }

        requestPermissionLauncher.launch(permission)

        continuation.invokeOnCancellation {
            permissionCallback = null
        }
    }
} 