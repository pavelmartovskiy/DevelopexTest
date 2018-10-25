package com.pm.developextest.app

import com.pm.developextest.core.di.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            ActivitiesBindingModule::class
        ])

interface AppComponent : AndroidInjector<DevelopExTestApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: DevelopExTestApp): Builder
        fun build(): AppComponent
    }
}