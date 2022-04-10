package com.gravitycode.bitcoinraffle.view

import android.view.ViewGroup

/**
 * Wanted to use an inline `value` class but this does not work with Android Hilt library.
 * */
data class ActivityView(val content: ViewGroup)