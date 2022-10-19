package com.ukadovlad21.goalcounterv2.screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ukadovlad21.goalcounterv2.DataHolder
import com.ukadovlad21.goalcounterv2.DialogTimesPicker
import com.ukadovlad21.goalcounterv2.MAIN
import com.ukadovlad21.goalcounterv2.R
import com.ukadovlad21.goalcounterv2.adapter.ChoseAdapter
import com.ukadovlad21.goalcounterv2.databinding.FragmentTeamsBinding
import kotlinx.android.synthetic.main.fragment_list.*


class TeamsFragment : Fragment() {
    private lateinit var binding: FragmentTeamsBinding
    lateinit var adapter: ChoseAdapter
    lateinit var recyclerView: RecyclerView

    private val gameList: ArrayList<Int> = arrayListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial()

        addTextListeners()

        binding.btnBack.setOnClickListener {
            MAIN.navController.navigate(R.id.action_teamsFragment_to_mainFragment)
        }
        binding.btnStart.setOnClickListener {
            toMatchFragment()
        }
    }

    private fun addTextListeners() {
        binding.tieFirst.error = "Min Length - ${getString(R.string.min_length)}"
        binding.tieSecond.error = "Min Length - ${getString(R.string.min_length)}"
        binding.btnStart.isEnabled = false
        val watcherS = object: SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                s?.also {
                    if (it.length < (getString(R.string.min_length)).toInt()) {
                        if (binding.tieFirst.isFocused) {
                            binding.tieFirst.error = "Min Length - ${getString(R.string.min_length)}"
                        } else {
                            binding.tieSecond.error = "Min Length - ${getString(R.string.min_length)}"
                        }
                        binding.btnStart.setBackgroundResource(R.drawable.button_style_disabled)
                    } else {
                        if (binding.tieFirst.isFocused) {
                            binding.tieFirst.error = null
                        } else {
                            binding.tieSecond.error = null
                        }
                    }
                }
                if (binding.tieFirst.text!!.length>=3 &&binding.tieSecond.text!!.length>=3) {
                    binding.btnStart.setBackgroundResource(R.drawable.button_style)
                    binding.btnStart.isEnabled = true
                } else {
                    binding.btnStart.isEnabled = false

                }
            }
        }

        binding.tieFirst.addTextChangedListener(watcherS)
        binding.tieSecond.addTextChangedListener(watcherS)



    }






    private fun toMatchFragment() {
        val firstTeamName = binding.tieFirst.text.toString()
        val secondTeamName = binding.tieSecond.text.toString()
        val choosedGame = gameList[adapter.newPosition]

        val bundle = Bundle()

        bundle.putString("FTN", firstTeamName)
        bundle.putString("STN", secondTeamName)

        if (choosedGame == R.drawable.ic_custom) {
            val dialog = DialogTimesPicker(1,10,10,99,bundle)
            dialog.show(parentFragmentManager,"dialog")
        }
        else {
            val dataHolder = DataHolder()
            val half = dataHolder.halfsInGame(choosedGame)
            val duration = dataHolder.durationsInGame(choosedGame)

            bundle.putInt("Half", half)
            bundle.putLong("Duration", duration)
            MAIN.navController.navigate(R.id.action_teamsFragment_to_matchFragment, args = bundle)

        }

        // MAIN.navController.navigate(R.id.action_teamsFragment_to_matchFragment, args = bundle)
        //Что значит эта команда.чем она отличается от
        //TODO fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment,matchFragment)?.commit()


    }

    private fun initial() {

        recyclerView = binding.rvGameList
        adapter = ChoseAdapter()
        recyclerView.layoutManager = LinearLayoutManager(parentFragment?.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        adapter.setList(choseList())
    }

    private fun choseList(): ArrayList<Int> {

        gameList.add(R.drawable.ic_football)
        gameList.add(R.drawable.ic_basketball)
        gameList.add(R.drawable.ic_hockey)
        gameList.add(R.drawable.ic_handball)
        gameList.add(R.drawable.ic_water_polo)
        gameList.add(R.drawable.ic_polo)
        gameList.add(R.drawable.ic_custom)

        return gameList
    }




}

open class SimpleTextWatcher: TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
    }

}
