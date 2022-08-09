package com.furkan.plate_reader_camerax_vision.presentation.camera

import androidx.camera.core.CameraSelector
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.furkan.plate_reader_camerax_vision.core.utils.encodeBase64
import com.furkan.plate_reader_camerax_vision.core.utils.getCameraProvider
import com.furkan.plate_reader_camerax_vision.presentation.ui.components.ProgressBar
import com.furkan.plate_reader_camerax_vision.presentation.ui.components.RectangleShape
import com.furkan.plate_reader_camerax_vision.presentation.ui.components.camera.CameraPreview
import com.furkan.plate_reader_camerax_vision.presentation.ui.components.camera.CapturePictureButton
import timber.log.Timber

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    viewModel: CameraViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.imageCaptureChannel.collect { event ->
            when (event) {
                is CameraViewModel.ScanPhotoEvent.SuccessEvent -> {
                    onNavigate("scan_screen/${event.resource.data!!.encodeBase64()}")
                }
                is CameraViewModel.ScanPhotoEvent.ErrorEvent -> {
                    println(event.resource.message?.asString(context))
                }
            }
        }
    }


    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(viewModel.previewUseCase.value) {
        val cameraProvider = context.getCameraProvider()
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA,
                viewModel.previewUseCase.value, viewModel.myImageCapture
            )
        } catch (ex: Exception) {
            Timber.e(ex, "Failed to bind camera use cases")
        }
    }


    Box(modifier = modifier) {

        CameraPreview(
            modifier = modifier,
            onUseCase = {
                viewModel.onPreviewUseCaseChange(it)
            }
        )

        RectangleShape()

        CapturePictureButton(
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            onClick = {
                viewModel.captureImage()
            },
            enabled = !viewModel.isLoading.value
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
            ) {
                if (viewModel.isLoading.value) ProgressBar()
            }
        }

    }

}