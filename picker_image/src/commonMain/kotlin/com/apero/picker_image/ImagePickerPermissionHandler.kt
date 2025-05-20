package com.apero.picker_image

interface ImagePickerPermissionHandler {
    suspend fun requestGalleryPermission(): Boolean
    suspend fun requestCameraPermission(): Boolean
    fun hasGalleryPermission(): Boolean
    fun hasCameraPermission(): Boolean
}

expect class PlatformImagePickerPermissionHandler() : ImagePickerPermissionHandler {
    override suspend fun requestGalleryPermission(): Boolean
    override suspend fun requestCameraPermission(): Boolean
    override fun hasGalleryPermission(): Boolean
    override fun hasCameraPermission(): Boolean
} 