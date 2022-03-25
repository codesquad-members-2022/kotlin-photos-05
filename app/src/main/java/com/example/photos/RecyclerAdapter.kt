package com.example.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.photos.databinding.ColorSquareBinding

class RecyclerAdapter(private var dataset: MutableList<Int>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(binding: ColorSquareBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ColorSquareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setBackgroundColor(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}