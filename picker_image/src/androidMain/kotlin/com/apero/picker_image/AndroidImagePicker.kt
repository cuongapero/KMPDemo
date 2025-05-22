package com.apero.picker_image

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Android implementation of ImagePicker using Activity Result API
 * @property activity The activity context needed for launching the picker
 */
class AndroidImagePicker(private val activity: ComponentActivity) : ImagePicker {
    private var resultCallback: ((Uri?) -> Unit)? = null
    private var permissionCallback: ((Boolean) -> Unit)? = null
    private lateinit var getContent: ActivityResultLauncher<String>
    private lateinit var requestPermission: ActivityResultLauncher<String>

    fun setup() {
        setupActivityResult()
        setupPermissionResult()
    }

    fun setupActivityResult() {
        getContent = activity.registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            resultCallback?.let { callback ->
                callback(uri)
                resultCallback = null
            }
        }
    }

    private fun setupPermissionResult() {
        requestPermission = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionCallback?.let { callback ->
                callback(isGranted)
                permissionCallback = null
            }
        }
    }

    override suspend fun hasPermission(): Boolean {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            ContextCompat.checkSelfPermission(
//                activity,
//                Manifest.permission.READ_MEDIA_IMAGES
//            ) == PackageManager.PERMISSION_GRANTED
//        } else {
//            ContextCompat.checkSelfPermission(
//                activity,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED
//        }
        return true
    }

    override suspend fun requestPermission(): Boolean = suspendCancellableCoroutine { continuation ->
        permissionCallback = { isGranted ->
            continuation.resume(isGranted)
        }

        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        try {
            requestPermission.launch(permission)
        } catch (e: Exception) {
            continuation.resume(false)
        }

        continuation.invokeOnCancellation {
            permissionCallback = null
        }
    }

    override suspend fun pickImage(): ImageResult? = suspendCancellableCoroutine { continuation ->
        resultCallback = { uri ->
            val result = uri?.toString()?.let { ImageResult(it) }
            continuation.resume(result)
        }

        try {
            getContent.launch("image/*")
        } catch (e: Exception) {
            continuation.resume(null)
        }

        continuation.invokeOnCancellation {
            resultCallback = null
        }
    }
} 