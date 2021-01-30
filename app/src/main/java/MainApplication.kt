package com.panchalamitr.sglivetraffic

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        /** To print logs **/
        Timber.plant(Timber.DebugTree())
    }

}
