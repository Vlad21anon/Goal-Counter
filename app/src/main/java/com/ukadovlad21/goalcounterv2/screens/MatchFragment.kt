package com.ukadovlad21.goalcounterv2.screens

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import com.google.android.material.dialog.MaterialDialogs
import com.ukadovlad21.goalcounterv2.MAIN
import com.ukadovlad21.goalcounterv2.R
import com.ukadovlad21.goalcounterv2.databinding.FragmentMatchBinding
import java.lang.StringBuilder

class MatchFragment : Fragment() {
    private lateinit var binding: FragmentMatchBinding

    private lateinit var timer:CountDownTimer;

    private var scoreFirstTeam: Int = 0
    private var scoreSecondTeam: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvScore.text = "$scoreFirstTeam:$scoreSecondTeam"

        setupButtons()
        setupData()

        startGame()

    }



    private fun setupButtons() {
        binding.btnCancel.setOnClickListener {
            MAIN.navController.navigate(R.id.action_matchFragment_to_teamsFragment)
        }

        binding.btnFinish.setOnClickListener {
            timer.cancel()
            toWinnerScreen()
        }

        binding.btnGoalFirst.setOnClickListener {
            binding.tvScore.text = "${++scoreFirstTeam}:$scoreSecondTeam"
            setTextSize()
        }
        binding.btnGoalSecond.setOnClickListener {
            binding.tvScore.text = "$scoreFirstTeam:${++scoreSecondTeam}"
            setTextSize()

        }
    }//

    private fun setTextSize() {
        binding.tvScore.textSize = (95*3/binding.tvScore.text.length).toFloat()
    }

    private fun setupData() {
        binding.tvFirstTeam.text = this.arguments?.get("FTN").toString()
        binding.tvSecondTeam.text = this.arguments?.get("STN").toString()

    }

    private fun startGame() {
        setupTimer(1)
    }

    private fun setupTimer(currentTime: Int) {
        val duration = this.arguments?.get("Duration").toString().toInt()
        val times = this.arguments?.get("Half").toString().toInt()

        binding.tvPeriod.text = "$currentTime Half"
        timer = object : CountDownTimer((duration.toLong()*1000),1_000) {
            override fun onTick(milisecond: Long) {
                var minutes:String = (milisecond / 60000).toString()
                var seconds:String =((milisecond/1000) %60).toString()
                if (seconds.toInt() <=9) { seconds = "0$seconds" }
                if (minutes.toInt() <=9) { minutes = "0$minutes" }

                val timeText = "${minutes}:${seconds}"
                binding.tvTimer.text = timeText
            }
            override fun onFinish() {
                if (currentTime == times) {
                    toWinnerScreen()
                }else {
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle("Time is over.")
                    dialog.setMessage("When the break is over, press the button!")
                    //dialog.setView(R.drawable.button_style)
                    dialog.setPositiveButton("Continue", DialogInterface.OnClickListener{_, _ ->
                        setupTimer(currentTime+1)
                    })
                    dialog.show()
                }
            }
        }
        timer.start()

    }

    private fun toWinnerScreen() {
        val bundle = saveData()

        MAIN.navController.navigate(R.id.action_matchFragment_to_winnerFragment,bundle)
    }



    private fun saveData():Bundle {
        val bundle = Bundle()
        bundle.putString("FirstTeam",binding.tvFirstTeam.text.toString())
        bundle.putString("SecondTeam",binding.tvSecondTeam.text.toString())
        bundle.putInt("FirstTeamGoals",scoreFirstTeam)
        bundle.putInt("SecondTeamGoals",scoreSecondTeam)

        return bundle
    }



}
