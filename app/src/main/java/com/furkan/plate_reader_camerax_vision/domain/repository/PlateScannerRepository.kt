package com.furkan.plate_reader_camerax_vision.domain.repository

import android.graphics.Bitmap
import android.net.Uri


interface PlateScannerRepository {

    suspend fun capturePhoto(): String

    suspend fun cropPhoto(capturedUri: Uri): Uri

    suspend fun scanPhoto(bitmap: Bitmap): List<String>
}