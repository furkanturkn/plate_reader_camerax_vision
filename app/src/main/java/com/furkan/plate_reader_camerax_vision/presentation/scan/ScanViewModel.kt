package com.furkan.plate_reader_camerax_vision.presentation.scan

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkan.plate_reader_camerax_vision.core.Constants
import com.furkan.plate_reader_camerax_vision.core.utils.Status
import com.furkan.plate_reader_camerax_vision.core.utils.decodeBase64
import com.furkan.plate_reader_camerax_vision.domain.use_case.CropPhotoUseCase
import com.furkan.plate_reader_camerax_vision.domain.use_case.ScanPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val cropPhotoUseCase: CropPhotoUseCase,
    private val scanPhotoUseCase: ScanPhotoUseCase
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _imageUri = mutableStateOf(Uri.parse(Constants.EMPTY_IMAGE_URI))
    val imageUri: State<Uri> = _imageUri

    fun convertPhoto(strUri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cropPhotoUseCase(strUri.decodeBase64().toUri()).collect { response ->
                when (response.status) {
                    Status.LOADING -> {
                        _isLoading.value = true
                    }
                    Status.SUCCESS -> {
                        _isLoading.value = false
                        _imageUri.value = response.data
                    }
                    Status.ERROR -> {
                        _isLoading.value = false
                        _imageUri.value = Uri.parse(Constants.EMPTY_IMAGE_URI)
                    }
                }
            }
        }
    }

    private val _scanResult = mutableStateOf("no_data")
    val scanResult: State<String> = _scanResult

    fun onScanPhoto(photoBitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            scanPhotoUseCase(photoBitmap).collect { response ->
                when (response.status) {
                    Status.LOADING -> {
                        Timber.d("scanPhotoUseCase LOADING")
                        _isLoading.value = true
                    }
                    Status.SUCCESS -> {
                        Timber.d("scanPhotoUseCase SUCCESS")
                        _scanResult.value = response.data!!
                        _isLoading.value = false
                    }
                    Status.ERROR -> {
                        Timber.d("scanPhotoUseCase ERROR")
                        _scanResult.value = "error scanning"
                        _isLoading.value = false
                    }
                }
            }


        }
    }
}