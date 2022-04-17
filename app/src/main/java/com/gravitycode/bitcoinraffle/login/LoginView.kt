package com.gravitycode.bitcoinraffle.login

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.gravitycode.bitcoinraffle.R
import com.gravitycode.bitcoinraffle.view.ActivityView
import com.gravitycode.bitcoinraffle.view.IView
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class LoginView @Inject constructor(
    inflater: LayoutInflater,
    parent: ActivityView
) : IView<LoginViewEvent> {

    override val contentView: View = inflater.inflate(R.layout.login_view, parent.content, false)
    override val toolbar: Toolbar? = null

    private val nameField: EditText = contentView.findViewById(R.id.text_field_name)
    private val btcAddressField: EditText = contentView.findViewById(R.id.text_field_btc_address)
    private val enterButton: Button = contentView.findViewById(R.id.btn_enter)

    private var _eventListener: ((LoginViewEvent) -> Unit)? = null

    init {
        enterButton.setOnClickListener {
            if (_eventListener != null) {
                val name = nameField.text.toString()
                val btcAddress = btcAddressField.text.toString()
                val loginViewEvent = LoginViewEvent(name, btcAddress)
                _eventListener!!.invoke(loginViewEvent)
            } else {
                throw IllegalStateException(
                    "No event listener set for {${javaClass.canonicalName}}"
                )
            }
        }
    }

    override fun setEventListener(listener: (LoginViewEvent) -> Unit) {
        _eventListener = listener
    }
}