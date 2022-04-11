package com.gravitycode.bitcoinraffle.util

import android.app.Activity
import android.view.ViewGroup
import android.widget.Toast

fun Activity.getContentView(): ViewGroup = window.decorView.findViewById(android.R.id.content)

fun Activity.showToast(msg: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, msg, duration).show()
}