package com.gravitycode.bitcoinraffle.raffle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gravitycode.bitcoinraffle.R
import com.gravitycode.bitcoinraffle.view.IView

class RaffleView(
    layoutInflater: LayoutInflater,
    root: ViewGroup? = null,
    attachToRoot: Boolean = false
) : IView<RaffleViewEvent> {

    override val contentView: View =
        layoutInflater.inflate(R.layout.raffle_view, root, attachToRoot)

    private var eventListener: ((RaffleViewEvent) -> Unit)? = null

    init {

    }

    override fun setEventListener(listener: (RaffleViewEvent) -> Unit) {
        eventListener = listener
    }
}