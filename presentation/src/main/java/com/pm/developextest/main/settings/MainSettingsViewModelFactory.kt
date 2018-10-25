package com.pm.developextest.main.settings

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.pm.developextest.data.UrlExplorer

class MainSettingsViewModelFactory(val urlExplorer: UrlExplorer): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainSettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainSettingsViewModel(urlExplorer) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}