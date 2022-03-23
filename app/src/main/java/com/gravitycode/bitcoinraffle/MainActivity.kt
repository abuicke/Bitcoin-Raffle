package com.gravitycode.bitcoinraffle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gravitycode.bitcoinraffle.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = UserViewHolderAdapter()
        adapter.users.add(
            User(
                "https://i.imgur.com/K72XGlf.png",
                getString(R.string.test_btc_wallet_address)
            )
        )
        adapter.users.add(
            User(
                "https://i.imgur.com/K72XGlf.png",
                getString(R.string.test_btc_wallet_address)
            )
        )
        adapter.users.add(
            User(
                "https://i.imgur.com/K72XGlf.png",
                getString(R.string.test_btc_wallet_address)
            )
        )
        binding.recyclerViewUsers.adapter = adapter
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this.baseContext)
    }
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
        holder.btcAddress.text = user.btcWalletAddress
    }

    override fun getItemCount() = users.size
}