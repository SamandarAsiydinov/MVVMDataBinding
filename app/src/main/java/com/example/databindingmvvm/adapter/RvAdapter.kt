package com.example.databindingmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.databindingmvvm.R
import com.example.databindingmvvm.databinding.ItemLayoutBinding
import com.example.databindingmvvm.model.RecyclerData

class RvAdapter : ListAdapter<RecyclerData, RvAdapter.RvViewHolder>(DiffCallBack()) {

    private class DiffCallBack : DiffUtil.ItemCallback<RecyclerData>() {
        override fun areItemsTheSame(oldItem: RecyclerData, newItem: RecyclerData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: RecyclerData, newItem: RecyclerData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater)
        return RvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RvViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RecyclerData) {
            binding.recyclerData = data
            binding.executePendingBindings()
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(imageView: AppCompatImageView, url: String) {
            Glide.with(imageView)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView)
        }
    }
}