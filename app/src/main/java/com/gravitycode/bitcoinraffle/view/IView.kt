package com.gravitycode.bitcoinraffle.view

import android.view.View
import kotlinx.coroutines.flow.StateFlow

interface IView<T> {

    fun getContentView(): View

    fun setEventListener(listener: (T) -> Unit)
}