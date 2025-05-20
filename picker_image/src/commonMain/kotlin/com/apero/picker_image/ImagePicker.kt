package com.apero.picker_image

/**
 * Result class containing the picked image information
 * @property uri The URI or file path of the picked image
 */
data class ImageResult(
    val uri: String
)

/**
 * Common interface for image picking functionality across platforms
 */
interface ImagePicker {
    /**
     * Checks if the app has permission to access photos
     * @return true if permission is granted, false otherwise
     */
    suspend fun hasPermission(): Boolean

    /**
     * Requests permission to access photos
     * @return true if permission was granted, false otherwise
     */
    suspend fun requestPermission(): Boolean

    /**
     * Picks a single image from the device's gallery
     * @return ImageResult containing the picked image URI/path, or null if user cancelled
     */
    suspend fun pickImage(): ImageResult?
} 