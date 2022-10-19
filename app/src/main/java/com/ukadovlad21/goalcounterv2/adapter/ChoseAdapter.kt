package com.ukadovlad21.goalcounterv2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ukadovlad21.goalcounterv2.R
import com.ukadovlad21.goalcounterv2.databinding.IcChoseLayoutBinding
import com.ukadovlad21.goalcounterv2.model.ImageModel
import kotlinx.android.synthetic.main.ic_chose_layout.view.*

class ChoseAdapter: RecyclerView.Adapter<ChoseAdapter.ChoseViewHolder>() {
    private var choseList = emptyList<Int>()

    var newPosition: Int = 0
    private var oldPosition: Int = -1

    private fun setSelectedPosition(position: Int) {
        oldPosition = newPosition
        newPosition = position
        notifyItemChanged(newPosition)
        notifyItemChanged(oldPosition)
    }

    class ChoseViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding: IcChoseLayoutBinding by lazy {
            IcChoseLayoutBinding.bind(view)
        }

        fun bind(image: Int, isSelected: Boolean) {
            binding.imageElemRv.setImageResource(image)

            if (isSelected) {
                binding.imageElemRv.setBackgroundResource(R.drawable.button_style)
            }
            else {
                binding.imageElemRv.background = null
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoseViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.ic_chose_layout,parent,false)
        return ChoseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChoseViewHolder, position: Int) {
        holder.bind(choseList[position], position == newPosition)
        holder.itemView.setOnClickListener {
            setSelectedPosition(position)
        }

    }




    override fun getItemCount(): Int {
        return choseList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Int>) {
        choseList = list
        notifyDataSetChanged()
    }
}

