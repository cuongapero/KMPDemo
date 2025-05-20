package com.apero.kmpdemo.domain.model

data class Category(
    val id: String,
    val name: String,
    val styles: List<Style> = emptyList()
) 