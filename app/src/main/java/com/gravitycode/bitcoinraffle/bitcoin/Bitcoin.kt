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
        /**
         * The main or “production” network where people buy and sell things.
         * */
        MAIN {
            override fun getNetworkParameters(): NetworkParameters = MainNetParams.get()
        },

        /**
         * Regression test mode, which is not a public network and requires
         * you to run a bitcoin daemon with the -regtest flag yourself.
         * */
        REG {
            override fun getNetworkParameters(): NetworkParameters = RegTestParams.get()
        },

        /**
         * The public test network (testnet) which is reset from time
         * to time and exists for us to play about with new features.
         * */
        TEST {
            override fun getNetworkParameters(): NetworkParameters = TestNet3Params.get()
        },

        /**
         * Network parameters used by the bitcoinj unit tests (and potentially your own). This
         * lets you solve a block using Block.solve() by setting difficulty to the easiest possible.
         * */
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
