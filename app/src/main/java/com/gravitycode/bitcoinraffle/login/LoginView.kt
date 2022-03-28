package com.gravitycode.bitcoinraffle.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.gravitycode.bitcoinraffle.R
import com.gravitycode.bitcoinraffle.view.IView

class LoginView(
    layoutInflater: LayoutInflater,
    root: ViewGroup? = null,
    attachToRoot: Boolean = false
) : IView<LoginViewEvent> {

    override val contentView: View = layoutInflater.inflate(R.layout.login_view, root, attachToRoot)

    private var eventListener: ((LoginViewEvent) -> Unit)? = null

    private val nameField: EditText = contentView.findViewById(R.id.text_field_name)
    private val btcAddressField: EditText = contentView.findViewById(R.id.text_field_btc_address)
    private val enterButton: Button = contentView.findViewById(R.id.btn_enter)

    init {
        enterButton.setOnClickListener {
            if (eventListener != null) {
                val name = nameField.text.toString()
                val btcAddress = btcAddressField.text.toString()
                val loginViewEvent = LoginViewEvent(name, btcAddress)
                eventListener!!.invoke(loginViewEvent)
            } else {
                throw IllegalStateException(
                    "You must set an event listener on objects of type {${javaClass.canonicalName}}"
                )
            }
        }
    }

    override fun setEventListener(listener: (LoginViewEvent) -> Unit) {
        eventListener = listener
    }
}