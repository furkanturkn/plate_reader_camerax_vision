package com.furkan.plate_reader_camerax_vision.core.utils

import android.content.ContentResolver
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.graphics.PathParser
import com.furkan.plate_reader_camerax_vision.core.Constants
import com.furkan.plate_reader_camerax_vision.core.Constants.RECTANGLE_PATH
import java.io.ByteArrayOutputStream

object PhotoUtils {


    fun getCroppedBitmap(src: Bitmap, path: Path?): Bitmap {
        val output = Bitmap.createBitmap(
            src.width,
            src.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawPath(path!!, paint)
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(src, 0f, 0f, paint)
        return output
    }

    fun getRectanglePath(src: Bitmap?): Path {
        return resizePath(
            PathParser.createPathFromPathData(RECTANGLE_PATH),
            src!!.width.toFloat(), src.height.toFloat()
        )
    }

    private fun resizePath(path: Path?, width: Float, height: Float): Path {
        val bounds = RectF(0F, 0F, width, height)
        val resizedPath = Path(path)
        val src = RectF()
        resizedPath.computeBounds(src, true)
        val resizeMatrix = Matrix()
        resizeMatrix.setRectToRect(src, bounds, Matrix.ScaleToFit.CENTER)
        resizedPath.transform(resizeMatrix)
        return resizedPath
    }




    fun Uri.isEmptyImageUri(contentResolver: ContentResolver): Uri {

        if (this == Uri.parse(Constants.EMPTY_IMAGE_URI)) return Uri.parse(Constants.EMPTY_IMAGE_URI)

        val myBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, this))
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, this)
        }
        return myBitmap.convertToUri(contentResolver)
    }


    fun Bitmap.convertToUri(contentResolver: ContentResolver): Uri {
        val bytes = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            contentResolver,
            this,
            "Title" + System.currentTimeMillis(),
            null
        )
        return Uri.parse(path)
    }

    fun Uri.convertToBitmap(contentResolver: ContentResolver): Bitmap {
        return MediaStore.Images.Media.getBitmap(contentResolver, this)
    }
}