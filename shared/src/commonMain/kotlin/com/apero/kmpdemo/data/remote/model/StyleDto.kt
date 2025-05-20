package com.apero.kmpdemo.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StyleDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("category_id")
    val categoryId: String,
    @SerialName("thumbnail")
    val thumbnail: String
) 