package com.miibarra.marsroverphotos.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miibarra.marsroverphotos.data.remote.responses.Photo
import com.miibarra.marsroverphotos.data.repository.RoverRepositoryImpl
import com.miibarra.marsroverphotos.utils.Constants
import com.miibarra.marsroverphotos.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val repository: RoverRepositoryImpl
): ViewModel() {

    private var currentPage = 0
    private var roverName = "curiosity"
    private var numSol = 1000
    private var photo: Photo? = null

    var photoEntry = mutableStateOf(photo)
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun getPhotoDetail(photoId: Int) {
        viewModelScope.launch {
            isLoading.value = true

            val result = repository.getRoverPhotoListBySol(roverName, numSol, Constants.PAGE_SIZE, currentPage * Constants.PAGE_SIZE)
            Log.e("PhotoDetailViewModel", "Starting result to photo detail view model")
            when (result) {
                is Resource.Success -> {
                    val photoResult = result.data?.photos?.map { entry ->
                        Photo(
                            entry.camera,
                            entry.earth_date,
                            entry.id,
                            entry.img_src,
                            entry.rover,
                            entry.sol
                        )
                    }
                    photoResult?.forEach {
                        if (it.id == photoId) {
                            photoEntry.value = it
                        }
                    }
                    loadError.value = ""
                    isLoading.value = false
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}