package com.apero.composeapp.presentation.home

import com.apero.composeapp.presentation.base.MviSingleEvent


sealed interface HomeEvent : MviSingleEvent {
    data class ShowError(val message: String) : HomeEvent
    data class NavigateToCategory(val categoryId: String) : HomeEvent
} 