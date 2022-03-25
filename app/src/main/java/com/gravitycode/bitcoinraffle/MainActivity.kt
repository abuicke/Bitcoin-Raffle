package com.gravitycode.bitcoinraffle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gravitycode.bitcoinraffle.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: RaffleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = UserViewHolderAdapter()

        repeat(20) {
            adapter.users.add(
                User(
                    "Mushroom",
                    "https://i.imgur.com/K72XGlf.png",
                    getString(R.string.test_btc_wallet_address)
                )
            )
        }

        binding.recyclerViewUsers.adapter = adapter
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this.baseContext)
    }
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name: TextView = itemView.findViewById(R.id.text_name)
    val profilePic: ImageView = itemView.findViewById(R.id.img_profile_pic)
    val btcWalletAddress: TextView = itemView.findViewById(R.id.text_btc_address)
}

class UserViewHolderAdapter() : RecyclerView.Adapter<UserViewHolder>() {

    val users: ArrayList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val userListItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)
        return UserViewHolder(userListItem)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        Picasso.get()
            .load(user.profilePicUrl)
            .into(holder.profilePic)
        holder.name.text = user.name
        holder.btcWalletAddress.text = user.btcWalletAddress
    }

    override fun getItemCount() = users.size
}