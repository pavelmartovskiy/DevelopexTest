package com.pm.developextest.app

import dagger.android.support.DaggerApplication
import io.reactivex.internal.functions.Functions
import io.reactivex.plugins.RxJavaPlugins



class DevelopExTestApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler(Functions.emptyConsumer())
    }

    override fun applicationInjector() = DaggerAppComponent.builder()
        .application(this)
        .build()
}