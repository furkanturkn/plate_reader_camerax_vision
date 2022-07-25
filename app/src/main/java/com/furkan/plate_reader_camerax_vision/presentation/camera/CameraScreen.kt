package com.furkan.plate_reader_camerax_vision.presentation.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.furkan.plate_reader_camerax_vision.presentation.ui.components.RectangleShape
import com.furkan.plate_reader_camerax_vision.presentation.ui.components.camera.CameraPreview
import com.furkan.plate_reader_camerax_vision.presentation.ui.components.camera.CapturePictureButton

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
) {

    Box(modifier = modifier) {

        CameraPreview(
            modifier = modifier,
            onUseCase = {
                //TODO: it will provide from view-model
            }
        )

        RectangleShape()

        CapturePictureButton(
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            onClick = {
                onNavigate("convert_screen/TEST")
            },
            enabled = true
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
            ) {
                //TODO: it will provide from view-model
            }
        }

    }

}