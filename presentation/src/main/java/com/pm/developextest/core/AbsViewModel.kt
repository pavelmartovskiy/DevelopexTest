package com.pm.developextest.core

import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry

abstract class AbsViewModel : ViewModel(), Observable {
    @Transient
    private val callbacks = PropertyChangeRegistry()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    protected fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}