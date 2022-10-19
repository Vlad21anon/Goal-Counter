package com.ukadovlad21.goalcounterv2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ukadovlad21.goalcounterv2.R
import com.ukadovlad21.goalcounterv2.Winner
import com.ukadovlad21.goalcounterv2.databinding.WinnerListViewBinding

class WinnerListAdapter(private val winnerList : ArrayList<Winner>):
    RecyclerView.Adapter<WinnerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WinnerHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.winner_list_view, parent, false)
        return WinnerHolder(view)
    }

    override fun getItemCount(): Int {
        return winnerList.size
    }

    override fun onBindViewHolder(holder: WinnerHolder, position: Int) {
        holder.onBind(winnerList[position])
    }
}

class WinnerHolder(view: View): RecyclerView.ViewHolder(view) {
    private lateinit var binding: WinnerListViewBinding

    fun onBind(winner: Winner) {
        binding = WinnerListViewBinding.bind(itemView)

        val scoresGreenTeam =winner.score.substringBefore(":")
        val scoresRedTeam = winner.score.substringAfter(":")
        if (scoresRedTeam>scoresGreenTeam) {
            binding.ivWinnerList.setBackgroundResource(R.drawable.ic_playerl)
            binding.tvScoreList.text = scoresRedTeam + ":" + scoresGreenTeam
            binding.tvWinnerList.text = winner.name
        } else if (scoresRedTeam<scoresGreenTeam){
            binding.ivWinnerList.setBackgroundResource(R.drawable.ic_playerw)
            binding.tvScoreList.text = winner.score
            binding.tvWinnerList.text = winner.name
        }else {
            binding.tvScoreList.text = winner.score
            binding.tvWinnerList.text = "No winners"
            binding.ivWinnerList.visibility = View.GONE
        }
    }
}