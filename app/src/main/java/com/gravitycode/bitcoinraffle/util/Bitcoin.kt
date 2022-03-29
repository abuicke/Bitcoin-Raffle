package com.gravitycode.bitcoinraffle.util

import com.gravitycode.bitcoinraffle.BuildConfig
import org.bitcoinj.core.Address
import org.bitcoinj.core.AddressFormatException
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.params.RegTestParams
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.params.UnitTestParams
import timber.log.Timber

object Bitcoin {

    enum class NetworkType {
        MAIN, REG, TEST, UNIT_TEST
    }

    fun isValidBtcWallet(
        address: String,
        networkType: NetworkType = if (BuildConfig.DEBUG) NetworkType.TEST else NetworkType.MAIN
    ): Boolean {

        val networkParams = when (networkType) {
            NetworkType.MAIN -> MainNetParams.get()
            NetworkType.REG -> RegTestParams.get()
            NetworkType.TEST -> TestNet3Params.get()
            NetworkType.UNIT_TEST -> UnitTestParams.get()
        }

        return try {
            Address.fromString(networkParams, address)
            true
        } catch (afe: AddressFormatException) {
            Timber.w(afe)
            false
        }
    }
}
