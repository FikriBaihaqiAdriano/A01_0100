package com.example.uasa1

import android.app.Application
import com.example.uasa1.dependeciesinjection.AppContainer
import com.example.uasa1.dependeciesinjection.ManajemenContainer

class ManajemenApplications : Application() {
    lateinit var ManajemenContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        ManajemenContainer = ManajemenContainer()
    }
}
