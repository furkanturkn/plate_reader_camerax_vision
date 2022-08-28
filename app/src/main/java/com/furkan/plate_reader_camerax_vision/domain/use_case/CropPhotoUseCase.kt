package com.furkan.plate_reader_camerax_vision.domain.use_case

import android.net.Uri
import com.furkan.plate_reader_camerax_vision.R
import com.furkan.plate_reader_camerax_vision.core.utils.Resource
import com.furkan.plate_reader_camerax_vision.core.utils.UiText
import com.furkan.plate_reader_camerax_vision.domain.repository.PlateScannerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CropPhotoUseCase @Inject constructor(
    private val plateScannerRepository: PlateScannerRepository
) {

    operator fun invoke(capturedUri: Uri): Flow<Resource<Uri>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(plateScannerRepository.cropPhoto(capturedUri)))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.error(
                    UiText.StringResource(
                        resId = R.string.err_convert_image,
                        e.localizedMessage ?: "Unknown error"
                    ), null
                )
            )
        }
    }

}