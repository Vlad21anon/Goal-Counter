package com.ukadovlad21.goalcounterv2.screens

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import com.ukadovlad21.goalcounterv2.DataHolder
import com.ukadovlad21.goalcounterv2.MAIN
import com.ukadovlad21.goalcounterv2.R
import com.ukadovlad21.goalcounterv2.databinding.FragmentWinnerBinding
import kotlinx.android.synthetic.main.fragment_winner.*
import java.io.File
import java.io.FileOutputStream


class WinnerFragment : Fragment() {
    private lateinit var binding: FragmentWinnerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWinnerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setResult()
        setupButtons()
    }
    private fun setResult() {
        val firstTeam = this.arguments?.getString("FirstTeam")
        val secondTeam = this.arguments?.getString("SecondTeam")
        val firstTeamGoals = this.arguments?.getInt("FirstTeamGoals")
        val secondTeamGoals = this.arguments?.getInt("SecondTeamGoals")

        if (firstTeamGoals!!.toInt()>secondTeamGoals!!.toInt()) {
            binding.tvWinnerName.text = firstTeam
            binding.tvLoserName.text = secondTeam
            binding.tvScore.text = "$firstTeamGoals:$secondTeamGoals"
            binding.ivWinner.setImageResource(R.drawable.ic_playerw)
            binding.ivLoser.setImageResource(R.drawable.ic_playerl)
        }else {
            binding.tvWinnerName.text = secondTeam
            binding.tvLoserName.text = firstTeam
            binding.tvScore.text = "$firstTeamGoals:$secondTeamGoals"
            binding.ivWinner.setImageResource(R.drawable.ic_playerl)
            binding.ivLoser.setImageResource(R.drawable.ic_playerw)
        }

        saveWinner(binding.tvWinnerName.text.toString(),binding.tvScore.text.toString())

    }
    private fun saveWinner(teamName:String,goals: String) {
        DataHolder.instance().addWinner(teamName,goals)
    }



    private fun setupButtons() {
        binding.btnWinnerList.setOnClickListener {
            MAIN.navController.navigate(R.id.action_winnerFragment_to_listFragment)
        }
        binding.btnMenu.setOnClickListener {
            MAIN.navController.navigate(R.id.action_winnerFragment_to_mainFragment)
        }

        binding.btnShare.setOnClickListener {
            //Tooast(Environment.getDownloadCacheDirectory().toString())
            shareResult()
        }
    }

    private fun Tooast(string: String) {
        Toast.makeText(requireContext(),string,Toast.LENGTH_SHORT).show()
    }

//    private fun shareResults() {
//        val screenshotBitmap = makeScreenshotBitmap() //Возвращает Bitmap
//        val file = File(MAIN.externalCacheDir,"myImage.png")
//
//        val fOut = FileOutputStream(file)
//        screenshotBitmap.compress(Bitmap.CompressFormat.PNG,90,fOut)
//        fOut.flush()
//        fOut.close()
//
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
//        intent.setType("image/png")
//        startActivity(intent)
//
//
//    }

    private fun shareResult() {
        val cache = Cache(requireContext())
        val screenshotUri = cache.saveToCacheAndGetUri(makeScreenshotBitmap())
        val photoIntent = Intent(Intent.ACTION_SEND)

        photoIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
        photoIntent.type = "image/*"
        photoIntent.action = Intent.ACTION_SEND
        startActivity(Intent.createChooser(photoIntent, "Winner screen"))
    }

//    private fun makeUriFromBitmap(bitmap: Bitmap): Uri {
//
//        val file = File(Environment.getDownloadCacheDirectory()+"/screenshot"+".png")
//        val stream = FileOutputStream(file)
//
//        bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        stream.flush()
//        stream.close()
//        return Uri.fromFile(file)
//    }

    private fun makeScreenshotBitmap(): Bitmap {
        binding.btnMenu.visibility = View.GONE
        binding.btnShare.visibility = View.GONE
        binding.btnWinnerList.visibility = View.GONE
        val screenshot = binding.clMain.drawToBitmap()
        binding.btnMenu.visibility = View.VISIBLE
        binding.btnShare.visibility = View.VISIBLE
        binding.btnWinnerList.visibility = View.VISIBLE
        return screenshot

    }


}


