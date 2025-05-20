package com.apero.picker_image

import com.apero.picker_image.ImagePicker
import com.apero.picker_image.ImageResult
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSURL
import platform.Photos.*
import platform.UIKit.*
import platform.darwin.NSObject
import kotlin.coroutines.resume

class IosImagePicker(
    private val viewController: UIViewController
) : ImagePicker {
    private val delegate = PickerDelegate()
    private var continuation: CancellableContinuation<ImageResult?>? = null
    private var permissionContinuation: CancellableContinuation<Boolean>? = null

    override suspend fun hasPermission(): Boolean {
        return PHPhotoLibrary.authorizationStatus() == PHAuthorizationStatusAuthorized
    }

    override suspend fun requestPermission(): Boolean = suspendCancellableCoroutine { continuation ->
        permissionContinuation = continuation
        PHPhotoLibrary.requestAuthorization { status ->
            val isGranted = status == PHAuthorizationStatusAuthorized
            permissionContinuation?.resume(isGranted)
            permissionContinuation = null
        }
        continuation.invokeOnCancellation {
            permissionContinuation = null
        }
    }

    override suspend fun pickImage(): ImageResult? = suspendCancellableCoroutine { continuation ->
        this.continuation = continuation

        val picker = UIImagePickerController().apply {
            sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
            delegate = delegate
        }

        viewController.presentViewController(picker, animated = true, completion = null)

        continuation.invokeOnCancellation {
            this.continuation = null
            picker.dismissViewControllerAnimated(true, completion = null)
        }
    }

    private inner class PickerDelegate : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
        override fun imagePickerController(
            picker: UIImagePickerController,
            didFinishPickingMediaWithInfo: Map<Any?, *>
        ) {
            val imageUrl = didFinishPickingMediaWithInfo[UIImagePickerControllerReferenceURL] as? NSURL
            val result = imageUrl?.absoluteString?.let { ImageResult(it) }

            continuation?.resume(result)
            continuation = null

            picker.dismissViewControllerAnimated(true, completion = null)
        }

        override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
            continuation?.resume(null)
            continuation = null

            picker.dismissViewControllerAnimated(true, completion = null)
        }
    }
}
