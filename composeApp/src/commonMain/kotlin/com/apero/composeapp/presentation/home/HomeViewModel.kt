package com.apero.composeapp.presentation.home

import androidx.lifecycle.viewModelScope
import com.apero.kmpdemo.domain.usecase.GetStyleHomeUseCase
import com.apero.composeapp.presentation.base.MviViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : MviViewModel<HomeIntent, HomeState, HomeEvent>(), KoinComponent {
    private val getStyleHomeUseCase: GetStyleHomeUseCase by inject()

    override fun initState(): HomeState = HomeState()

    override fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadCategories -> loadCategories()
            is HomeIntent.SelectCategory -> {

            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }
            try {
                val categories = getStyleHomeUseCase()
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        banners = categories.first().styles,
                        trending = categories.first(),
                        categories = categories,
                    )
                }
            } catch (e: Exception) {
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                sendEvent(HomeEvent.ShowError(e.message ?: "Unknown error"))
            }
        }
    }
}