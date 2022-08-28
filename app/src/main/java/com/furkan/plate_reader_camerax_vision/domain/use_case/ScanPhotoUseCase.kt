package com.furkan.plate_reader_camerax_vision.domain.use_case

import android.graphics.Bitmap
import com.furkan.plate_reader_camerax_vision.R
import com.furkan.plate_reader_camerax_vision.core.Constants
import com.furkan.plate_reader_camerax_vision.core.utils.Resource
import com.furkan.plate_reader_camerax_vision.core.utils.UiText
import com.furkan.plate_reader_camerax_vision.domain.repository.PlateScannerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScanPhotoUseCase @Inject constructor(
    private val plateScannerRepository: PlateScannerRepository
) {
    operator fun invoke(bitmap: Bitmap): Flow<Resource<String>> = flow {

        try {
            emit(Resource.loading(null))
            val mostValuableData = findMostValuableData(plateScannerRepository.scanPhoto(bitmap))
            emit(Resource.success(mostValuableData))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.error(
                    UiText.StringResource(
                        resId = R.string.err_scan_image,
                        e.localizedMessage ?: "Unknown error"
                    ), null
                )
            )
        }


    }

    private fun findMostValuableData(ocrResultList: List<String>): String {

        val regexList = listOf(
            Constants.PLATE_REGEX_TEMPLATE_1,
            Constants.PLATE_REGEX_TEMPLATE_2,
            Constants.PLATE_REGEX_TEMPLATE_3
        )
        val stringBuilder = StringBuilder()

        for (data in ocrResultList) {
            for (regex in regexList) {
                if (Regex(regex).matches(data)) {
                    stringBuilder.append(data + "\n")
                }
            }
        }

        if (stringBuilder.isEmpty()) {
            for (data in ocrResultList) {
                stringBuilder.append(data + "\n")
            }
        }

        return stringBuilder.toString()
    }


}