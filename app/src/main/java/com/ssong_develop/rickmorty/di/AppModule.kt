package com.ssong_develop.rickmorty.di

import android.app.Application
import com.ssong_develop.core_common.di.ApplicationScope
import com.ssong_develop.core_common.di.DefaultDispatcher
import com.ssong_develop.rickmorty.utils.PixelRatio
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
}