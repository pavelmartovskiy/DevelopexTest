package com.pm.developextest.core.extension

import android.support.annotation.LayoutRes
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(@LayoutRes id:Int ):View =
        from(context).inflate(id, this, false)
