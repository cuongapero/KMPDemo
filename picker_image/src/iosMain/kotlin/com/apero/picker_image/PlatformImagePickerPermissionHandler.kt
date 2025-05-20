package com.apero.picker_image

import kotlinx.coroutines.suspendCancellableCoroutine
import platform.AVFoundation.*
import platform.Photos.*
import kotlin.coroutines.resume

actual class PlatformImagePickerPermissionHandler : ImagePickerPermissionHandler {

    actual override fun hasGalleryPermission(): Boolean {
        return PHPhotoLibrary.authorizationStatus() == PHAuthorizationStatusAuthorized
    }

    actual override fun hasCameraPermission(): Boolean {
        return AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) == AVAuthorizationStatusAuthorized
    }

    actual override suspend fun requestGalleryPermission(): Boolean =
        suspendCancellableCoroutine { continuation ->
            when (val status = PHPhotoLibrary.authorizationStatus()) {
                PHAuthorizationStatusAuthorized -> continuation.resume(true)
                PHAuthorizationStatusNotDetermined -> {
                    PHPhotoLibrary.requestAuthorization { newStatus ->
                        if (continuation.isActive) {
                            continuation.resume(newStatus == PHAuthorizationStatusAuthorized)
                        }
                    }
                }

                else -> continuation.resume(false)
            }
        }

    actual override suspend fun requestCameraPermission(): Boolean =
        suspendCancellableCoroutine { continuation ->
            when (val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)) {
                AVAuthorizationStatusAuthorized -> continuation.resume(true)
                AVAuthorizationStatusNotDetermined -> {
                    AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                        if (continuation.isActive) {
                            continuation.resume(granted)
                        }
                    }
                }

                else -> continuation.resume(false)
            }
        }
}
