package com.ukadovlad21.goalcounterv2.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.recyclerview.widget.LinearLayoutManager
import com.ukadovlad21.goalcounterv2.DataHolder
import com.ukadovlad21.goalcounterv2.MAIN
import com.ukadovlad21.goalcounterv2.R
import com.ukadovlad21.goalcounterv2.adapter.WinnerListAdapter
import com.ukadovlad21.goalcounterv2.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding

    private lateinit var adapter: WinnerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupList()

        super.onViewCreated(view, savedInstanceState)
        binding.btnClear.setOnClickListener {
            DataHolder.instance().winnerList.clear()
            adapter.notifyDataSetChanged()
            Toast.makeText(context,"History cleared", Toast.LENGTH_SHORT).show()
        }

        binding.btnMenu.setOnClickListener {
            MAIN.navController.navigate(R.id.action_listFragment_to_mainFragment)
        }
    }


    private fun setupList() {
        adapter = WinnerListAdapter(DataHolder.instance().winnerList)

        binding.rvGameList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvGameList.adapter = adapter
    }


}