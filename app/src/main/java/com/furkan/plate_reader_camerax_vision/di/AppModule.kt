package com.furkan.plate_reader_camerax_vision.di

import android.content.Context
import androidx.camera.core.ImageCapture
import com.furkan.plate_reader_camerax_vision.data.repository.PlateScannerRepositoryImpl
import com.furkan.plate_reader_camerax_vision.domain.repository.PlateScannerRepository
import com.google.android.gms.vision.text.TextRecognizer
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
        textRecognizer: TextRecognizer
    ): PlateScannerRepository {
        return PlateScannerRepositoryImpl(appContext, imageCapture, textRecognizer)
    }

    @Singleton
    @Provides
    fun provideImageCaptureBuilder(): ImageCapture {
        return ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .build()
    }

    @Singleton
    @Provides
    fun provideTextRecognizerBuilder(
        @ApplicationContext appContext: Context,
    ): TextRecognizer {
        return TextRecognizer.Builder(appContext).build()
    }
}