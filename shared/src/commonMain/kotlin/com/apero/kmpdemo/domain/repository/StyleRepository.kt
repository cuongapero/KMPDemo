package com.apero.kmpdemo.domain.repository

import com.apero.kmpdemo.domain.model.Category

interface StyleRepository {
    suspend fun getStyleHome(): List<Category>
} 