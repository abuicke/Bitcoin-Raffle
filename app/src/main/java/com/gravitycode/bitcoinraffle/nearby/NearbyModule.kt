package com.gravitycode.bitcoinraffle.nearby

import android.content.Context
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.ConnectionsClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NearbyModule {

    @Singleton
    @Provides
    fun provideConnectionsClient(@ApplicationContext context: Context): ConnectionsClient {
        return Nearby.getConnectionsClient(context)
    }

    @Singleton
    @Provides
    fun provideDiscoverUsersService(
        @ApplicationContext context: Context,
        connectionsClient: ConnectionsClient
    ): DiscoverUsersService {
        return DiscoverUsersServiceImpl(context, connectionsClient)
    }
}