package com.gravitycode.bitcoinraffle.user

import android.content.SharedPreferences
import com.google.common.collect.ImmutableList
import com.gravitycode.bitcoinraffle.util.StrictSharedPreferences
import javax.inject.Inject

class UsersRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private companion object {
        const val USERNAME = "signedInUser-name"
        const val BTC_WALLET = "signedInUser-btcWallet"
    }

    private var loggedInUser: User? = null

    init {
        val strictSharedPreferences = StrictSharedPreferences(sharedPreferences)

        val loggedInUserNameExists = sharedPreferences.contains(USERNAME)
        val loggedInUserWalletExists = sharedPreferences.contains(BTC_WALLET)

        if (loggedInUserNameExists && loggedInUserWalletExists) {
            val name = strictSharedPreferences.getString(USERNAME)
            val btcAddress = strictSharedPreferences.getString(BTC_WALLET)
            loggedInUser = User(name, btcAddress)
        }
    }

    /**
     * If null then no user is logged in.
     * */
    fun getLoggedInUser() = loggedInUser

    fun setLoggedInUser(user: User) {
        sharedPreferences.edit()
            .putString(USERNAME, user.name)
            .putString(BTC_WALLET, user.btcWalletAddress)
            .apply()
    }

    fun getAllUsers(): ImmutableList<User> {
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