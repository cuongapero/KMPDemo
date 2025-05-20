package com.apero.picker_image

import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Photos.*
import platform.UIKit.UIViewController
import kotlin.coroutines.resume

/**
 * iOS implementation of permission handler
 */
class IosPermissionHandler(
    private val viewController: UIViewController
) : PermissionHandler {
    
    override suspend fun checkPermission(): PermissionState {
        return when (PHPhotoLibrary.authorizationStatus()) {
            PHAuthorizationStatusAuthorized,
            PHAuthorizationStatusLimited -> PermissionState.GRANTED
            PHAuthorizationStatusDenied -> PermissionState.PERMANENTLY_DENIED
            PHAuthorizationStatusNotDetermined -> PermissionState.DENIED
            else -> PermissionState.DENIED
        }
    }

    override suspend fun requestPermission(): PermissionState = suspendCancellableCoroutine { continuation ->
        PHPhotoLibrary.requestAuthorization { status ->
            val state = when (status) {
                PHAuthorizationStatusAuthorized,
                PHAuthorizationStatusLimited -> PermissionState.GRANTED
                PHAuthorizationStatusDenied -> PermissionState.PERMANENTLY_DENIED
                PHAuthorizationStatusNotDetermined -> PermissionState.DENIED
                else -> PermissionState.DENIED
            }
            continuation.resume(state)
        }
    }
} 