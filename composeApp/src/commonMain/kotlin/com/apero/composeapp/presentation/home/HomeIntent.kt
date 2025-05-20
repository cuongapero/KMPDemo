package com.apero.composeapp.presentation.home

import com.apero.composeapp.presentation.base.MviIntent
import com.apero.kmpdemo.domain.model.Category


sealed interface HomeIntent : MviIntent {
    data object LoadCategories : HomeIntent
    data class SelectCategory(val category: Category) : HomeIntent
} 