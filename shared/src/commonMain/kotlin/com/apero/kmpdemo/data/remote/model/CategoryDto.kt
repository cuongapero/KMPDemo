package com.apero.kmpdemo.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("banner_url")
    val bannerUrl: String,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    @SerialName("styles")
    val styles: List<StyleDto> = emptyList()
) 