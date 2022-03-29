package com.gravitycode.bitcoinraffle.app

import android.app.Application
import com.gravitycode.bitcoinraffle.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BitcoinRaffleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            val debugTree = Timber.DebugTree()
            Timber.plant(debugTree)
        }
    }
}