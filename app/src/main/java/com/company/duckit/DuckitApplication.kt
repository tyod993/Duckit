package com.company.duckit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DuckitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}