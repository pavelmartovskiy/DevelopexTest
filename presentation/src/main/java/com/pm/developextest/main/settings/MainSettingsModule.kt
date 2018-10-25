package com.pm.developextest.main.settings

import android.arch.lifecycle.ViewModelProviders
import com.pm.developextest.core.di.ActivityScope
import com.pm.developextest.core.di.FragmentScope
import com.pm.developextest.data.UrlExplorer
import dagger.Module
import dagger.Provides

@Module
class MainSettingsModule {

    @Provides
    @FragmentScope
    fun provideViewModelFactory(urlExplorer: UrlExplorer) = MainSettingsViewModelFactory(urlExplorer)

    @Provides
    @FragmentScope
    fun provideViewModel(
        fragment: MainSettingsDialogFragment,
        factory: MainSettingsViewModelFactory
    ) = ViewModelProviders
        .of(fragment,factory)
        .get(MainSettingsViewModel::class.java)


}