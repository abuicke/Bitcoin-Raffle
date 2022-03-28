package com.gravitycode.bitcoinraffle.raffle

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gravitycode.bitcoinraffle.R
import com.squareup.picasso.Picasso

class ParticipantViewHolderAdapter() : RecyclerView.Adapter<ParticipantViewHolder>() {

    var uiState: RaffleUiState = RaffleUiState()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val userListItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)
        return ParticipantViewHolder(userListItem)
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        val user = uiState.participants[position]
        Picasso.get()
            .load(user.profilePicUri)
            .into(holder.profilePic)
        holder.name.text = user.name
        holder.btcWalletAddress.text = user.btcWalletAddress
        if (user === uiState.winner) {
            holder.background.setBackgroundColor(Color.YELLOW)
        }
    }

    override fun getItemCount() = uiState.participants.size
}