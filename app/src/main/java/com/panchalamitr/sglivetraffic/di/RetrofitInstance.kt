package com.panchalamitr.sglivetraffic.com.panchalamitr.sglivetraffic.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.panchalamitr.sglivetraffic.common.Constants
import com.panchalamitr.sglivetraffic.data.remote.TrafficImageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class RetrofitInstance {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideNetworkAdapter(): NetworkResponseAdapterFactory {
        return NetworkResponseAdapterFactory()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        networkResponseAdapterFactory: NetworkResponseAdapterFactory
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(networkResponseAdapterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttp)
    }

    @Singleton
    @Provides
    fun provideTrafficImages(retrofitBuilder: Retrofit.Builder): TrafficImageService {
        return retrofitBuilder.build().create(TrafficImageService::class.java)
    }

    private val okHttp: OkHttpClient
        get() {
            val loggingInterceptor = LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .log(Log.VERBOSE)
                .build()
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }
}
