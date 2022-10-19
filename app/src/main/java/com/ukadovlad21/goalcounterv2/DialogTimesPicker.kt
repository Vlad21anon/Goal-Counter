package com.ukadovlad21.goalcounterv2

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.ukadovlad21.goalcounterv2.databinding.DialogTimerSettingBinding
import com.ukadovlad21.goalcounterv2.screens.SimpleTextWatcher
import java.lang.NumberFormatException

class DialogTimesPicker(
    private val minHalf: Int,
    private val maxHalf: Int,
    private val minDuration: Long,
    private val maxDuration: Long,
    private val bundle: Bundle
): DialogFragment()
{
    private lateinit var binding : DialogTimerSettingBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogTimerSettingBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)

        setupTextWatcher()


        binding.btnGo.setOnClickListener {
            saveInfo()

            dismiss()
            MAIN.navController.navigate(R.id.action_teamsFragment_to_matchFragment, args = bundle)

        }


        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog

    }

    private fun saveInfo() {
        val half = binding.tieTimes.text.toString().toInt()
        val duration = binding.tieDuration.text.toString().toLong()
        bundle.putInt("Half", half)
        bundle.putLong("Duration", duration*60)
    }

    private fun setupTextWatcher() {
        binding.tieTimes.error = "From $minHalf to $maxHalf"
        binding.tieDuration.error = "From $minDuration to $maxDuration"
        binding.btnGo.isEnabled = binding.tieTimes.error == null && binding.tieDuration.error == null


        val twMinLength = object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                s?.also {
                    val input: Int
                    try {
                        input = it.toString().toInt()
                    }
                    catch (exception: NumberFormatException) {
                        return@also
                    }
                    if (binding.tieTimes.isFocused) {
                        if (input in minHalf..maxHalf) {
                            binding.tieTimes.error = null
                        }
                        else {
                            binding.tieTimes.error = "From $minHalf to $maxHalf"
                        }
                    }
                    else if (binding.tieDuration.isFocused) {
                        if (input in minDuration..maxDuration) {
                            binding.tieDuration.error = null
                        }
                        else {
                            binding.tieDuration.error = "From $minDuration to $maxDuration"
                        }
                    }
                }
                binding.btnGo.isEnabled = binding.tieTimes.error == null && binding.tieDuration.error == null
            }
        }

        binding.tieTimes.addTextChangedListener(twMinLength)
        binding.tieDuration.addTextChangedListener(twMinLength)

    }


}
