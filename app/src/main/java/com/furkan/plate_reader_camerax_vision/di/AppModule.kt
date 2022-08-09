package com.furkan.plate_reader_camerax_vision.di

import android.content.Context
import androidx.camera.core.ImageCapture
import com.furkan.plate_reader_camerax_vision.data.repository.PlateScannerRepositoryImpl
import com.furkan.plate_reader_camerax_vision.domain.repository.PlateScannerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlateReaderRepository(
        @ApplicationContext appContext: Context,
        imageCapture: ImageCapture,
    ): PlateScannerRepository {
        return PlateScannerRepositoryImpl(appContext, imageCapture)
    }

    @Singleton
    @Provides
    fun provideImageCaptureBuilder(): ImageCapture {
        return ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .build()
    }
}