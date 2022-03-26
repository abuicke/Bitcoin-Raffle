package com.gravitycode.bitcoinraffle.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gravitycode.bitcoinraffle.R
import com.gravitycode.bitcoinraffle.view.IView

class LoginView(
    layoutInflater: LayoutInflater,
    root: ViewGroup? = null,
    attachToRoot: Boolean = false
) : IView {

    override val contentView: View = layoutInflater.inflate(R.layout.login_view, root, attachToRoot)
}