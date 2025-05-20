package com.apero.composeapp.presentation.home

import com.apero.composeapp.presentation.base.MviViewState
import com.apero.kmpdemo.domain.model.Category
import com.apero.kmpdemo.domain.model.Style

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val banners: List<Style> = emptyList(),
    val trending: Category? = null,
    val categories: List<Category> = emptyList(),
) : MviViewState