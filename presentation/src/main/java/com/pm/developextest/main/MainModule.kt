package com.pm.developextest.main

import android.arch.lifecycle.ViewModelProviders
import com.pm.developextest.core.di.ActivityScope
import com.pm.developextest.data.UrlExplorer
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    @ActivityScope
    fun provideViewModelFactory(urlExplorer: UrlExplorer) = MainViewModelFactory(urlExplorer)

    @Provides
    @ActivityScope
    fun provideViewModel(
        activity: MainActivity,
        factory: MainViewModelFactory
    ) = ViewModelProviders
        .of(activity,factory)
        .get(MainViewModel::class.java)

    @Provides
    @ActivityScope
    fun provideAdapter() = MainAdapter()

}