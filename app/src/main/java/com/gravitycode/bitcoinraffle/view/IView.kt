package com.gravitycode.bitcoinraffle.view

import android.view.View
import androidx.appcompat.widget.Toolbar

interface IView<T> {

    val contentView: View

    val toolbar: Toolbar?

    fun setEventListener(listener: (T) -> Unit)
}