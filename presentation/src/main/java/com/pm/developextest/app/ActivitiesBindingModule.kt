package com.pm.developextest.app

import com.pm.developextest.core.di.ActivityScope
import com.pm.developextest.main.MainActivity
import com.pm.developextest.main.MainFragmentsBindingModule
import com.pm.developextest.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesBindingModule {
  @ActivityScope
  @ContributesAndroidInjector(modules = [MainModule::class, MainFragmentsBindingModule::class])
  fun contributeMainActivity(): MainActivity
}