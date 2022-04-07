package com.gravitycode.bitcoinraffle.nearby

import android.content.Context
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.gravitycode.bitcoinraffle.users.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class DiscoverUsersServiceImpl(
    context: Context,
    connectionsClient: ConnectionsClient
) : DiscoverUsersService {

    private val connectionsClientWrapper = ConnectionsClientWrapper(context, connectionsClient)

    /**
     * TODO: Remove
     * */
    @OptIn(DelicateCoroutinesApi::class)
    override fun discoverUsers(): Flow<User> {
        val flow = MutableSharedFlow<User>()
        GlobalScope.launch {
            val threeSeconds = 3.toDuration(DurationUnit.SECONDS)
            delay(threeSeconds)
            listOf(
                User(
                    "Mario",
                    "mvwnsN7YSvcaL1zxhouxnCbgP4EhhENg53",
                    "https://flyclipart.com/thumb2/bit-mario-png-487410.png"
                ),
                User(
                    "Bowser",
                    "mkiy86BKw6YnwGByzYman1UsodRQbuG8Pg",
                    "https://i.pinimg.com/originals/04/7a/05/047a0583b7d91315d28992a1a80e8c59.png"
                ),
                User(
                    "Peach",
                    "mxYLRSQudm4MzrjgjsBe2wvb32SJgsvMmu"
                )//, "https://i.pinimg.com/236x/28/69/fd/2869fdd5a2fc963801a8dfe433023376.jpg")
            ).forEach {
                flow.emit(it)
            }
        }

        return flow
    }
}