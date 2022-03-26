package com.gravitycode.bitcoinraffle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gravitycode.bitcoinraffle.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * TODO: Needs to be implemented using dependency injection.
     * Don't understand how `by viewModels()` is supposed to work.
     * */
    private val viewModel: RaffleViewModel = RaffleViewModel(UsersRepository())//by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ParticipantViewHolderAdapter()
        binding.recyclerViewParticipants.adapter = adapter
        binding.recyclerViewParticipants.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    adapter.uiState = uiState
                    adapter.notifyDataSetChanged()
                }
            }
        }

        viewModel.fetchRaffleParticipants()
    }
}

class ParticipantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name: TextView = itemView.findViewById(R.id.text_name)
    val profilePic: ImageView = itemView.findViewById(R.id.img_profile_pic)
    val btcWalletAddress: TextView = itemView.findViewById(R.id.text_btc_address)
    val background: View = itemView.findViewById(R.id.background)
}

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
            .load(user.profilePicUrl)
            .into(holder.profilePic)
        holder.name.text = user.name
        holder.btcWalletAddress.text = user.btcWalletAddress
        if (user === uiState.winner) {
            holder.background.setBackgroundColor(Color.YELLOW)
        }
    }

    override fun getItemCount() = uiState.participants.size
}