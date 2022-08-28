package com.furkan.plate_reader_camerax_vision.presentation.scan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.rememberImagePainter
import com.furkan.plate_reader_camerax_vision.core.utils.PhotoUtils.convertToBitmap
import com.furkan.plate_reader_camerax_vision.core.utils.decodeBase64
import com.furkan.plate_reader_camerax_vision.presentation.ui.components.ProgressBar
import com.furkan.plate_reader_camerax_vision.presentation.ui.theme.Typography


@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
    strUri: String,
    viewModel: ScanViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.convertPhoto(strUri)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }



    Box(
        modifier = modifier
    ) {

        Image(
            modifier = modifier,
            painter = rememberImagePainter(
                data = viewModel.imageUri.value
            ),
            contentDescription = "Captured image"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            viewModel.scanResult.value,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(Color.White),
            style = Typography.h3,

            )

        Button(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                viewModel.onScanPhoto(
                    viewModel.imageUri.value
                        .convertToBitmap(context.contentResolver)
                )
            },
            enabled = !viewModel.isLoading.value
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
            ) {
                if (viewModel.isLoading.value) ProgressBar()
                else Text("Scan photo")
            }
        }



    }

}