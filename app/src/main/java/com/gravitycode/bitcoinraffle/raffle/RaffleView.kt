package com.gravitycode.bitcoinraffle.raffle

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gravitycode.bitcoinraffle.R
import com.gravitycode.bitcoinraffle.app.MainActivity
import com.gravitycode.bitcoinraffle.users.User
import com.gravitycode.bitcoinraffle.view.ActivityView
import com.gravitycode.bitcoinraffle.view.IView
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class RaffleView @Inject constructor(
    @ActivityContext context: Context,
    inflater: LayoutInflater,
    parent: ActivityView,
    picasso: Picasso
) : IView<RaffleViewEvent> {

    @SuppressLint("InflateParams")
    private val _contentView: View = inflater.inflate(R.layout.raffle_view, parent.content, false)
    private var _eventListener: ((RaffleViewEvent) -> Unit)? = null

    private val searchButton: View = _contentView.findViewById(R.id.img_search)
    private val recyclerView: RecyclerView = _contentView.findViewById(R.id.recycler_view_users)
    private val usersRecyclerAdapter: UserRecyclerViewAdapter = UserRecyclerViewAdapter(picasso)

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

    override fun getContentView(): View = _contentView

    override fun setEventListener(listener: (RaffleViewEvent) -> Unit) {
        _eventListener = listener
    }

    fun displayUsers(users: List<User>) {
        usersRecyclerAdapter.setUsers(users)
    }
}