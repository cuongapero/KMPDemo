package com.apero.kmpdemo.data.remote.datasource

import com.apero.kmpdemo.data.remote.model.CategoryDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface StyleRemoteDataSource {
    suspend fun getStyleHome(): List<CategoryDto>
}

class StyleRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : StyleRemoteDataSource {
    override suspend fun getStyleHome(): List<CategoryDto> {
        return httpClient.get("api/styles/home").body()
    }
} 