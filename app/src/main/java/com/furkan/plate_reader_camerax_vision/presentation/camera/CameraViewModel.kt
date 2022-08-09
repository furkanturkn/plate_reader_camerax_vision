package com.furkan.plate_reader_camerax_vision.presentation.camera

import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkan.plate_reader_camerax_vision.core.utils.Resource
import com.furkan.plate_reader_camerax_vision.core.utils.Status
import com.furkan.plate_reader_camerax_vision.domain.use_case.CapturePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val capturePhotoUseCase: CapturePhotoUseCase,
    private val imageCapture: ImageCapture
) : ViewModel() {
    val myImageCapture = imageCapture

    sealed class ScanPhotoEvent {
        data class SuccessEvent(val resource: Resource<String>) : ScanPhotoEvent()
        data class ErrorEvent(val resource: Resource<String>) : ScanPhotoEvent()
    }

    private val _imageCaptureChannel = Channel<ScanPhotoEvent>()
    val imageCaptureChannel = _imageCaptureChannel.receiveAsFlow()

    private val _previewUseCase = mutableStateOf<UseCase>(Preview.Builder().build())
    val previewUseCase: State<UseCase> = _previewUseCase
    fun onPreviewUseCaseChange(previewUseCase: UseCase) {
        _previewUseCase.value = previewUseCase
    }

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun captureImage() =
        viewModelScope.launch(Dispatchers.IO) {
            capturePhotoUseCase().collect { response ->
                when (response.status) {
                    Status.LOADING -> {
                        _isLoading.value = true
                    }
                    Status.SUCCESS -> {
                        _imageCaptureChannel.send(ScanPhotoEvent.SuccessEvent(response))
                        _isLoading.value = false
                    }
                    Status.ERROR -> {
                        _imageCaptureChannel.send(ScanPhotoEvent.ErrorEvent(response))
                        _isLoading.value = false
                    }
                }
            }
        }

}