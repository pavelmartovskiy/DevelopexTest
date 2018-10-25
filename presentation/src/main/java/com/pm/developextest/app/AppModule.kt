package com.pm.developextest.app

import com.pm.developextest.core.di.ApplicationScope
import com.pm.developextest.data.UrlExplorer
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @ApplicationScope
    @Provides
    fun provideUrlExplore():UrlExplorer = UrlExplorer()
}