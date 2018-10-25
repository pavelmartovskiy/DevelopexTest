package com.pm.developextest.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.pm.developextest.data.UrlExplorer

class MainViewModelFactory(val urlExplorer: UrlExplorer): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(urlExplorer) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}