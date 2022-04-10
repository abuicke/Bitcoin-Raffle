package com.gravitycode.bitcoinraffle.util

import android.content.SharedPreferences

/**
 * Functions incomplete. Add as necessary.
 *
 * @see SharedPreferences
 * */
class StrictSharedPreferences(private val sharedPreferences: SharedPreferences) {

    private companion object {
        const val FAIL = "fail"
    }

    fun getString(key: String): String {
        val str = sharedPreferences.getString(key, FAIL)
        return if (str != FAIL) {
            str!!
        } else {
            throw IllegalArgumentException(
                "Invalid key. {$key} does not exist in Shared Preferences"
            )
        }
    }
}