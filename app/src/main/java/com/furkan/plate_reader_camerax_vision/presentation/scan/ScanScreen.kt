package com.furkan.plate_reader_camerax_vision.presentation.scan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.furkan.plate_reader_camerax_vision.core.utils.decodeBase64
import com.furkan.plate_reader_camerax_vision.presentation.ui.theme.Typography


@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
    strUri: String,
) {

    Box(
        modifier = modifier
    ) {

        Image(
            modifier = modifier,
            painter = rememberImagePainter(
                data = strUri.decodeBase64()
            ),
            contentDescription = "Captured image"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "NO_VALUE",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(Color.White),
            style = Typography.h3,

            )

        Button(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                //TODO: it will provide from view-model
            },
            enabled = true
        ) {
            Text("Scan photo")
        }



    }

}