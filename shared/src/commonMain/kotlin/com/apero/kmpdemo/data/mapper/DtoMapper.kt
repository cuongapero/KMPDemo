package com.apero.kmpdemo.data.mapper

import com.apero.kmpdemo.data.remote.model.CategoryDto
import com.apero.kmpdemo.data.remote.model.StyleDto
import com.apero.kmpdemo.domain.model.Category
import com.apero.kmpdemo.domain.model.Style


fun StyleDto.toDomain(): Style = Style(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    thumbnail = thumbnail
)

fun CategoryDto.toDomain(): Category = Category(
    id = id,
    name = name,
    styles = styles.map { it.toDomain() }
) 