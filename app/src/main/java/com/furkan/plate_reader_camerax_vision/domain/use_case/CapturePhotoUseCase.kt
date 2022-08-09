package com.furkan.plate_reader_camerax_vision.domain.use_case

import com.furkan.plate_reader_camerax_vision.R
import com.furkan.plate_reader_camerax_vision.core.utils.Resource
import com.furkan.plate_reader_camerax_vision.core.utils.UiText
import com.furkan.plate_reader_camerax_vision.domain.repository.PlateScannerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CapturePhotoUseCase @Inject constructor(
    private val plateScannerRepository: PlateScannerRepository
) {
    operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.loading(null))
            val result = plateScannerRepository.capturePhoto()
            emit(Resource.success(result))
        } catch (e: Exception) {
            emit(
                Resource.error(
                    UiText.StringResource(
                        resId = R.string.err_capture_image,
                        e.localizedMessage ?: "Unknown error"
                    ), null
                )
            )
        }
    }
}