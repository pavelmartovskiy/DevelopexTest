package com.pm.developextest.main

import com.pm.developextest.core.di.FragmentScope
import com.pm.developextest.main.settings.MainSettingsModule
import com.pm.developextest.main.settings.MainSettingsDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainFragmentsBindingModule {
  @FragmentScope
  @ContributesAndroidInjector(modules = [MainSettingsModule::class])
  fun contributeMainSettingsFragment(): MainSettingsDialogFragment
}