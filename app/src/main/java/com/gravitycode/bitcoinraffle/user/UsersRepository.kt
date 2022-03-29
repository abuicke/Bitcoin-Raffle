package com.gravitycode.bitcoinraffle.user

import android.content.SharedPreferences
import com.google.common.base.Throwables
import com.google.common.collect.ImmutableList
import com.gravitycode.bitcoinraffle.util.Bitcoin
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

    fun setLoggedInUser(user: User): Result<User> {
        val isValidName = user.name.isNotEmpty()
        val isValidBtcAddress = Bitcoin.isValidBtcWallet(user.btcWalletAddress)

        return if (isValidName && isValidBtcAddress) {
            sharedPreferences.edit()
                .putString(USERNAME, user.name)
                .putString(BTC_WALLET, user.btcWalletAddress)
                .apply()
            Result.success(user)
        } else if (!isValidName) {
            val throwable = Throwable("name must be at least one character long")
            Result.failure(throwable)
        } else {
            val throwable = Throwable("invalid bitcoin wallet address: ${user.btcWalletAddress}")
            Result.failure(throwable)
        }
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