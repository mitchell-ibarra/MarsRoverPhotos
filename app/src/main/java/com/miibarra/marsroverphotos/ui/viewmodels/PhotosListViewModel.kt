package com.miibarra.marsroverphotos.ui.viewmodels

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.miibarra.marsroverphotos.data.models.PhotoListEntry
import com.miibarra.marsroverphotos.data.repository.RoverRepositoryImpl
import com.miibarra.marsroverphotos.utils.Constants.PAGE_SIZE
import com.miibarra.marsroverphotos.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosListViewModel @Inject constructor(
    private val repository: RoverRepositoryImpl
): ViewModel() {

    private var currentPage = 0
    private var roverName = "curiosity"
    private var numSol = 1000

    var photoList = mutableStateOf<List<PhotoListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var lastPage = mutableStateOf(false)

    init {
        loadPhotosPaginated()
    }

    fun loadPhotosPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getRoverPhotoListBySol(roverName, numSol, PAGE_SIZE, currentPage * PAGE_SIZE)
            Log.e("PhotosListViewModel", "Starting the call to get stuff")
            when(result) {
                is Resource.Success -> {
                    lastPage.value = currentPage * PAGE_SIZE >= result.data!!.photos.size
                    val photoEntries = result.data.photos.mapIndexed { index, entry ->
                        Log.e("PhotosListViewModel", "image url is: " + entry.img_src)
                        PhotoListEntry(entry.rover.name, entry.img_src, entry.id)
                    }
                    currentPage++

                    loadError.value = ""
                    isLoading.value = false
                    photoList.value += photoEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun getDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}