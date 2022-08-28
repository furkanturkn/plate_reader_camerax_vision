package com.furkan.plate_reader_camerax_vision.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.SparseArray
import androidx.camera.core.ImageCapture
import com.furkan.plate_reader_camerax_vision.core.utils.PhotoUtils
import com.furkan.plate_reader_camerax_vision.core.utils.PhotoUtils.convertToBitmap
import com.furkan.plate_reader_camerax_vision.core.utils.PhotoUtils.convertToUri
import com.furkan.plate_reader_camerax_vision.core.utils.PhotoUtils.getCroppedBitmap
import com.furkan.plate_reader_camerax_vision.core.utils.PhotoUtils.isEmptyImageUri
import com.furkan.plate_reader_camerax_vision.core.utils.executor
import com.furkan.plate_reader_camerax_vision.core.utils.takePhoto
import com.furkan.plate_reader_camerax_vision.domain.repository.PlateScannerRepository
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer

class PlateScannerRepositoryImpl(
    private val appContext: Context,
    private val imageCapture: ImageCapture,
    private val textRecognizer: TextRecognizer
) : PlateScannerRepository {
    override suspend fun capturePhoto(): String {
        val fileUri = Uri.fromFile(imageCapture.takePhoto(appContext.executor))
        return fileUri.toString()
    }

    override suspend fun cropPhoto(capturedUri: Uri): Uri {
        val myCroppedBitmap = capturedUri.isEmptyImageUri(appContext.contentResolver)
            .convertToBitmap(appContext.contentResolver)

        return getCroppedBitmap(
            myCroppedBitmap,
            PhotoUtils.getRectanglePath(myCroppedBitmap)
        ).convertToUri(appContext.contentResolver)
    }

    override suspend fun scanPhoto(bitmap: Bitmap): List<String> {
        val frame = Frame.Builder().setBitmap(bitmap).build()
        val items: SparseArray<TextBlock> = textRecognizer.detect(frame)
        val ocrResultList: MutableList<String> = mutableListOf()
        for (i in 0 until items.size()) {
            ocrResultList.add(items.valueAt(i).value)
        }

        return ocrResultList
    }
}