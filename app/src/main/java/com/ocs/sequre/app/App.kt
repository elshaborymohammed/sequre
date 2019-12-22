package com.ocs.sequre.app

import com.compact.app.CompactApplication

class App : CompactApplication() {

    override fun onCreate() {
        super.onCreate()
        
        com.ocs.sequre.app.di.DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }
}