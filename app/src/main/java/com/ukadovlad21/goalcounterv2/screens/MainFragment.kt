package com.ukadovlad21.goalcounterv2.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ukadovlad21.goalcounterv2.MAIN
import com.ukadovlad21.goalcounterv2.R
import com.ukadovlad21.goalcounterv2.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnWinnerList.setOnClickListener {
            MAIN.navController.navigate(R.id.action_mainFragment_to_listFragment)
        }

        binding.btnNewGame.setOnClickListener {
            MAIN.navController.navigate(R.id.action_mainFragment_to_teamsFragment)
        }
    }

}