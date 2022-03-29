package com.gravitycode.bitcoinraffle.login

import com.gravitycode.bitcoinraffle.util.Strings

data class LoginUiState(
    val name: String = Strings.EMPTY,
    val btcWalletAddress: String = Strings.EMPTY,
    var login: Login = Login.NOT_LOGGED_IN,
    var error: Throwable? = null
)