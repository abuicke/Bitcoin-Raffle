package com.gravitycode.bitcoinraffle

import com.google.common.collect.ImmutableList

class UsersRepository {

    fun getAllUsers(): List<User> {
        val builder = ImmutableList.Builder<User>()
        repeat(20) {
            builder.add(
                User(
                    "Mushroom",
                    "https://i.imgur.com/K72XGlf.png",
                    "3JBb9T7H6xKGmtaZvq7pPCr5LTAuocM2Xf"
                )
            )
        }
        return builder.build()
    }
}