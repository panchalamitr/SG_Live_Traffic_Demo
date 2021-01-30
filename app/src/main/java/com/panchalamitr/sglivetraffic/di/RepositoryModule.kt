package com.panchalamitr.sglivetraffic.com.panchalamitr.sglivetraffic.di

import com.panchalamitr.sglivetraffic.respository.DefaultTrafficImagesRepository
import com.panchalamitr.sglivetraffic.respository.TrafficImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(impl: DefaultTrafficImagesRepository): TrafficImageRepository
}