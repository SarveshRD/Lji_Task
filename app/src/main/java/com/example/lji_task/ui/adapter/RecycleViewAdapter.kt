package com.example.lji_task.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lji_task.R
import com.example.lji_task.data.Item
import com.example.lji_task.databinding.ListItemLayoutBinding


class RecycleViewAdapter(private var data: List<Item>) :
    RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>() {

    companion object {
        private var onItemClickListener: OnItemClickListener? = null
    }

    private lateinit var binding: ListItemLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_layout,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val viewPopularNearYouData = data[position]
        with(holder.binding) {

            repoName.text = "Repository Name : "+viewPopularNearYouData.name
            repoId.text = "Repository ID : "+viewPopularNearYouData.id
            userName.text = "UserName : "+viewPopularNearYouData.fullName



        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun filterList(filteredList: List<Item>) {
        data = filteredList
        notifyDataSetChanged()
    }


    class MyViewHolder(val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.mcvRestaurantImg.setOnClickListener { v ->
                onItemClickListener!!.onItemClick(adapterPosition, v)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        Companion.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, v: View)
    }
}