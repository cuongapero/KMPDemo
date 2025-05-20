package com.apero.composeapp.presentation.home

import androidx.lifecycle.viewModelScope
import com.apero.composeapp.presentation.base.MviViewModel
import com.apero.kmpdemo.domain.usecase.GetStyleHomeUseCase
import com.apero.picker_image.ImagePicker
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class HomeViewModel(
    private val imagePicker: ImagePicker
) : MviViewModel<HomeIntent, HomeState, HomeEvent>(), KoinComponent {
    private val getStyleHomeUseCase: GetStyleHomeUseCase by inject()

    override fun initState(): HomeState = HomeState()

    override fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadCategories -> loadCategories()
            is HomeIntent.SelectCategory -> {
                // Handle category selection
            }

            is HomeIntent.PickImage -> pickImage()
        }
    }

    private fun pickImage() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }

//            try {
            if (!imagePicker.hasPermission()) {
                val granted = imagePicker.requestPermission()
                if (!granted) {
                    _viewState.update { it.copy(isLoading = false) }
                    sendEvent(HomeEvent.ShowError("Permission denied"))
                    return@launch
                }
            }

            imagePicker.pickImage()?.let { pickedImage ->
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        selectedImageUri = pickedImage.uri
                    )
                }
            } ?: run {
                // User cancelled the picker
                _viewState.update { it.copy(isLoading = false) }
            }
//            } catch (e: Exception) {
//                _viewState.update {
//                    it.copy(
//                        isLoading = false,
//                        error = e.message
//                    )
//                }
//                sendEvent(HomeEvent.ShowError(e.message ?: "Failed to pick image"))
//            }
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