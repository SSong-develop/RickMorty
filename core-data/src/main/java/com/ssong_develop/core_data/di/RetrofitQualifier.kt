package com.ssong_develop.core_data.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ResponseWrapperRetrofit

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ResponseNoWrapperRetrofit
