package com.apero.picker_image

/**
 * Common interface for handling permissions across platforms
 */
interface PermissionHandler {
    /**
     * Check if the required permission is granted
     * @return current permission state
     */
    suspend fun checkPermission(): PermissionState

    /**
     * Request the required permission
     * @return new permission state after request
     */
    suspend fun requestPermission(): PermissionState
} 