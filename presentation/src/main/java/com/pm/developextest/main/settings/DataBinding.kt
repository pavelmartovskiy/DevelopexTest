package com.pm.developextest.main.settings

import android.databinding.BindingAdapter
import android.support.design.widget.TextInputLayout
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.pm.developextest.data.UrlExplorerEngineState
import com.pm.developextest.data.UrlExplorerEngineState.*

@BindingAdapter("android:setError")
fun setError(v: TextInputLayout, error:Int?) {
    when(error) {
        null -> v.error = null
        else -> v.error = v.context.getText(error)
    }
}

