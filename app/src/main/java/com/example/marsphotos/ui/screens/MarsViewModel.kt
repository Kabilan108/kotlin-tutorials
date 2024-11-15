
package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsPhotoApplication
import com.example.marsphotos.data.MarsPhotoRepository
import com.example.marsphotos.data.NetworkMarsPhotoRepository
import com.example.marsphotos.model.MarsPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface MarsUiState {
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel(
    private val marsPhotoRepository: MarsPhotoRepository
) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                MarsUiState.Success(marsPhotoRepository.getMarsPhoto())
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }

    // Android doesn't let ViewModel get passed constructor args when its created
    // need to create a factory -> pattern for creating objects
    // this will retrieve marsPhotoRepository from the app container
    // createa a companion object so that any instances opf the view model with have this
    companion object {
        val  Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // APPLICATION_KEY is part of ViewModelProvider.AndroidViewModelFactory.Companion
                // and is used to find the apps MarsPhotoApplication objkect
                val application = (this[APPLICATION_KEY] as MarsPhotoApplication)
                val marsPhotoRepository = application.container.marsPhotoRepository
                MarsViewModel(marsPhotoRepository = marsPhotoRepository)
            }
        }
    }
}
