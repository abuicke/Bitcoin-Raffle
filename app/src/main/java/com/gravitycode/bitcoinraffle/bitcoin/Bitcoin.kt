package com.gravitycode.bitcoinraffle.bitcoin

import com.gravitycode.bitcoinraffle.BuildConfig
import org.bitcoinj.core.Address
import org.bitcoinj.core.AddressFormatException
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.params.RegTestParams
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.params.UnitTestParams
import timber.log.Timber

object Bitcoin {

    enum class NetworkType {
        MAIN {
            override fun getNetworkParameters(): NetworkParameters = MainNetParams.get()
        },
        REG {
            override fun getNetworkParameters(): NetworkParameters = RegTestParams.get()
        },
        TEST {
            override fun getNetworkParameters(): NetworkParameters = TestNet3Params.get()
        },
        UNIT_TEST {
            override fun getNetworkParameters(): NetworkParameters = UnitTestParams.get()
        };

        abstract fun getNetworkParameters(): NetworkParameters
    }

    fun isValidBtcWallet(
        address: String,
        networkType: NetworkType = if (BuildConfig.DEBUG) NetworkType.TEST else NetworkType.MAIN
    ): Boolean {
        val networkParams = networkType.getNetworkParameters()
        return try {
            Address.fromString(networkParams, address)
            true
        } catch (afe: AddressFormatException) {
            Timber.w(afe)
            false
        }
    }
}
