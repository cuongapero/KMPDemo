package com.apero.kmpdemo.data.repository

import com.apero.kmpdemo.data.local.datasource.StyleLocalDataSource
import com.apero.kmpdemo.data.mapper.toDomain
import com.apero.kmpdemo.data.remote.datasource.StyleRemoteDataSource
import com.apero.kmpdemo.domain.model.Category
import com.apero.kmpdemo.domain.repository.StyleRepository


class StyleRepositoryImpl(
    private val remoteDataSource: StyleRemoteDataSource,
    private val localDataSource: StyleLocalDataSource
) : StyleRepository {
    override suspend fun getStyleHome(): List<Category> {
        return try {
            val remoteData = remoteDataSource.getStyleHome().map { it.toDomain() }
            localDataSource.saveStyleHome(remoteData)
            remoteData
        } catch (e: Exception) {
            localDataSource.getStyleHome()
        }
    }
} 