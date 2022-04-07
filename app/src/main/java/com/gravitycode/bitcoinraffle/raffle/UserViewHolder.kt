package com.gravitycode.bitcoinraffle.raffle

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gravitycode.bitcoinraffle.R

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name: TextView = itemView.findViewById(R.id.text_name)
    val profilePic: ImageView = itemView.findViewById(R.id.img_profile_pic)
    val btcWalletAddress: TextView = itemView.findViewById(R.id.text_btc_address)
    val background: View = itemView.findViewById(R.id.background)
}