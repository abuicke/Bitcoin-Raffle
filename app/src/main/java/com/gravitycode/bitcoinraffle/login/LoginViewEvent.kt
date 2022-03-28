package com.gravitycode.bitcoinraffle.login

import com.gravitycode.bitcoinraffle.util.Strings

data class LoginViewEvent(
    val name: String = Strings.EMPTY,
    val btcAddress: String = Strings.EMPTY
)