package com.gravitycode.bitcoinraffle.util

import android.app.Activity
import android.view.ViewGroup

fun Activity.getContentView(): ViewGroup = window.decorView.findViewById(android.R.id.content)