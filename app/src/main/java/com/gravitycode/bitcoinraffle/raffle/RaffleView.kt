package com.gravitycode.bitcoinraffle.raffle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gravitycode.bitcoinraffle.R
import com.gravitycode.bitcoinraffle.users.User
import com.gravitycode.bitcoinraffle.view.ActivityView
import com.gravitycode.bitcoinraffle.view.IView
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class RaffleView @Inject constructor(
    @ActivityContext context: Context,
    inflater: LayoutInflater,
    parent: ActivityView,
    picasso: Picasso
) : IView<RaffleViewEvent> {

    override val contentView: View = inflater.inflate(R.layout.raffle_view, parent.content, false)
    override val toolbar: Toolbar = contentView.findViewById(R.id.toolbar)

    private val startButton: View = contentView.findViewById(R.id.btn_start_raffle)
    private val searchButton: View = contentView.findViewById(R.id.btn_search_for_raffle)
    private val walletButton: View = contentView.findViewById(R.id.btn_wallet)
    private val recyclerView: RecyclerView = contentView.findViewById(R.id.recycler_view_users)
    private val usersRecyclerAdapter: UserRecyclerViewAdapter = UserRecyclerViewAdapter(picasso)

    private var _eventListener: ((RaffleViewEvent) -> Unit)? = null

    init {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = usersRecyclerAdapter

        searchButton.setOnClickListener {
            if (_eventListener != null) {
                val raffleViewEvent = RaffleViewEvent(Raffle.STARTED)
                _eventListener!!.invoke(raffleViewEvent)
                searchButton.visibility = View.GONE
            } else {
                throw IllegalStateException(
                    "No event listener set for ${javaClass.canonicalName}"
                )
            }
        }
    }

    override fun setEventListener(listener: (RaffleViewEvent) -> Unit) {
        _eventListener = listener
    }

    fun setLoggedInUser(user: User) {
        toolbar.title = user.btcWalletAddress
    }

    fun displayUsers(users: List<User>) {
        usersRecyclerAdapter.setUsers(users)
    }
}