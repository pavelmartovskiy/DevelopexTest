package com.pm.developextest.main

import android.databinding.BindingAdapter
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.pm.developextest.data.UrlExplorerEngineState
import com.pm.developextest.data.UrlExplorerEngineState.*

@BindingAdapter("android:visibleWhenIdle")
fun visibleWhenIdle(v: View, engineState: UrlExplorerEngineState) {
    v.visibility = when(engineState) {
        Idle -> VISIBLE
        Running -> GONE
    }
}

@BindingAdapter("android:visibleWhenRunning")
fun visibleWhenRunning(v: View, engineState: UrlExplorerEngineState) {
    v.visibility = when(engineState) {
        Idle -> GONE
        Running -> VISIBLE
    }
}