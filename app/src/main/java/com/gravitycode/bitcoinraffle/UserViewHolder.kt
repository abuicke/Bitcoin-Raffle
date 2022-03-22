package com.gravitycode.bitcoinraffle

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val profilePic: ImageView = itemView.findViewById(R.id.img_profile_pic)
    val btcAddress: TextView = itemView.findViewById(R.id.text_btc_address)

}