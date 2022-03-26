package com.gravitycode.bitcoinraffle.app

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
import com.gravitycode.bitcoinraffle.R
import com.gravitycode.bitcoinraffle.databinding.ActivityMainBinding
import com.gravitycode.bitcoinraffle.home.HomeViewModel
import com.gravitycode.bitcoinraffle.home.LoginView
import com.gravitycode.bitcoinraffle.login.LoginViewModel
import com.gravitycode.bitcoinraffle.raffle.RaffleUiState
import com.gravitycode.bitcoinraffle.raffle.RaffleViewModel
import com.gravitycode.bitcoinraffle.settings.SettingsViewModel
import com.gravitycode.bitcoinraffle.view.IView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val raffleViewModel: RaffleViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(LoginView(layoutInflater))

//        val adapter = ParticipantViewHolderAdapter()
//        binding.recyclerViewParticipants.adapter = adapter
//        binding.recyclerViewParticipants.layoutManager = LinearLayoutManager(this)
//
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                raffleViewModel.uiState.collect { uiState ->
//                    adapter.uiState = uiState
//                    adapter.notifyDataSetChanged()
//                }
//            }
//        }
//
//        raffleViewModel.fetchRaffleParticipants()
    }

    fun setContentView(iView: IView) {
        super.setContentView(iView.contentView)
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