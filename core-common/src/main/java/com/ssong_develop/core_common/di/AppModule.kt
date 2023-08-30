package com.ssong_develop.core_common.di

import android.app.Application
import com.ssong_develop.core_common.PixelRatio
import com.ssong_develop.core_common.helper.ImageLoader
import com.ssong_develop.core_common.manager.SnackbarMessageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @ApplicationScope
    @Provides
    fun provideApplicationScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

    @Provides
    @Singleton
    fun providePixelRatio(
        application: Application
    ) = PixelRatio(application)

    @Provides
    @Singleton
    fun provideSnackbarMessageManager(
        @ApplicationScope coroutineScope: CoroutineScope
    ): SnackbarMessageManager = SnackbarMessageManager(coroutineScope)

    @Provides
    @Singleton
    fun provideImageLoader(
        @MainDispatcher mainDispatcher: CoroutineDispatcher,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ImageLoader = ImageLoader(mainDispatcher, ioDispatcher)
}