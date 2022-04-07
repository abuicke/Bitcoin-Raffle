package com.gravitycode.bitcoinraffle.raffle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gravitycode.bitcoinraffle.R
import com.gravitycode.bitcoinraffle.users.User
import com.gravitycode.bitcoinraffle.util.load
import com.squareup.picasso.Picasso

class UserRecyclerViewAdapter(
    private val picasso: Picasso
) : RecyclerView.Adapter<UserViewHolder>() {

    private var users: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val userListItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)
        return UserViewHolder(userListItem)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.name.text = user.name
        holder.btcWalletAddress.text = user.btcWalletAddress
        /**
         * TODO: Need to make sure image isn't left over from previous use of ViewHolder.
         * Should there be `sanitiseViewHolder(UserViewHolder)` function which is called
         * automatically at the beginning of `onBindViewHolder(UserViewHolder, Int)`?
         * */
        picasso.load(user.profilePicUri, R.drawable.ic_blank_user)
            .into(holder.profilePic)
    }

    override fun getItemCount() = users.size

    /**
     * TODO: Is there a more efficient way to do this?
     * */
    fun setUsers(users: List<User>) {
        this.users = users
        /**
         * This event does not specify what about the data set has changed,
         * forcing any observers to assume that all existing items and
         * structure may no longer be valid. LayoutManagers will be forced
         * to fully rebind and relayout all visible views.
         * */
        notifyDataSetChanged()
    }

}