package com.gravitycode.bitcoinraffle.view

import android.view.View
import kotlinx.coroutines.flow.StateFlow

interface IView<T> {

    val contentView: View

    fun setEventListener(listener: (T) -> Unit)
}