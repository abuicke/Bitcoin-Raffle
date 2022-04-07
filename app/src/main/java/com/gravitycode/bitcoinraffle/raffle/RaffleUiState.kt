package com.gravitycode.bitcoinraffle.raffle

import com.gravitycode.bitcoinraffle.users.User

data class RaffleUiState(
    val users: List<User> = emptyList(),
    var winner: User? = null
) {

    override fun toString() = users.map { user -> user.name }.toString()
}
