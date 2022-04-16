package com.gravitycode.bitcoinraffle.bitcoin

/**
 * TODO: Remove these imports if the KDoc remains the only code that uses them.
 * */
import com.gravitycode.bitcoinraffle.BuildConfig
import org.bitcoinj.core.*
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.params.RegTestParams
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.params.UnitTestParams
import org.bitcoinj.store.BlockStore
import org.bitcoinj.wallet.Wallet
import timber.log.Timber

/**
 * bitcoinj
 * ========
 *
 * Bitcoin transactions typically send money to a public elliptic curve key. The sender creates a
 * transaction containing the address of the recipient, where the address is an encoded form of a
 * hash of their public key. The recipient then signs a transaction claiming the coins with their
 * own private key. A key is represented with the [ECKey] class. [ECKey] can contain private keys,
 * or just public keys that are missing the private part.
 *
 * A typical application that wants to send and receive money needs at least a [BlockChain],
 * a [BlockStore], a [PeerGroup] and a [Wallet]. See [How things fit together](https://bitcoinj.org/how-things-fit-together)
 *
 * @see [https://bitcoinj.org/getting-started-java](https://bitcoinj.org/getting-started-java)
 * */
object Bitcoin {

    /**
     * There are multiple separate, independent Bitcoin networks.
     *
     * Each network has its own genesis block, its own port number and its own address prefix bytes
     * to prevent you accidentally trying to send coins across networks (which won’t work). These
     * facts are encapsulated into a [NetworkParameters] singleton object. As you can see, each
     * network has its own class and you fetch the relevant [NetworkParameters] object by calling
     * `get()` on one of those objects.
     * */
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

    /**
     * TODO: Should make this `Result<Boolean>` and provide a meaningful error based on weather an
     * [AddressFormatException] or an [AddressFormatException.WrongNetwork] was thrown. Although
     * [AddressFormatException.WrongNetwork] is a child of [AddressFormatException] so it should be
     * enough just to get the message from the throwable. Although using throwable messages as user-
     * visible errors is probably bad practice.
     * */
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
