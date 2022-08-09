package com.furkan.plate_reader_camerax_vision.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.camera.core.ImageCapture
import com.furkan.plate_reader_camerax_vision.core.utils.executor
import com.furkan.plate_reader_camerax_vision.core.utils.takePhoto
import com.furkan.plate_reader_camerax_vision.domain.repository.PlateScannerRepository

class PlateScannerRepositoryImpl(
    private val appContext: Context,
    private val imageCapture: ImageCapture
) : PlateScannerRepository {
    override suspend fun capturePhoto(): String {
        val fileUri = Uri.fromFile(imageCapture.takePhoto(appContext.executor))
        return fileUri.toString()
    }

    override suspend fun cropPhoto(capturedUri: Uri): Uri {
        TODO("Not yet implemented")
    }

    override suspend fun scanPhoto(bitmap: Bitmap): List<String> {
        TODO("Not yet implemented")
    }
}