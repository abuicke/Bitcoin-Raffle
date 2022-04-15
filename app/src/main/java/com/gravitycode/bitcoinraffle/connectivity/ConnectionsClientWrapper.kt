package com.gravitycode.bitcoinraffle.connectivity

import android.content.Context
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Strategy
import com.google.android.gms.tasks.Task

internal class ConnectionsClientWrapper(
    context: Context,
    private val connectionsClient: ConnectionsClient
) {

    private val serviceId = context.packageName
    private val strategy = Strategy.P2P_CLUSTER
    private val advertisingOptions = AdvertisingOptions.Builder().setStrategy(strategy).build()
    private val discoveryOptions = DiscoveryOptions.Builder().setStrategy(strategy).build()

    fun startAdvertising(name: String, callback: ConnectionLifecycleCallback): Task<Void> {
        return connectionsClient.startAdvertising(name, serviceId, callback, advertisingOptions)
    }

    fun startDiscovery(callback: EndpointDiscoveryCallback): Task<Void> {
        return connectionsClient.startDiscovery(serviceId, callback, discoveryOptions)
    }
}
