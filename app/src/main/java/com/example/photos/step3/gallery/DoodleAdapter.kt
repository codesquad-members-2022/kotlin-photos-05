package com.example.photos.step3.gallery

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photos.R
import com.example.photos.databinding.DoodleSquareBinding
import com.example.photos.step3.model.DoodleImage

class DoodleAdapter :
    ListAdapter<DoodleImage, DoodleAdapter.DoodleViewHolder>(MyDoodleDiffCallback) {

    inner class DoodleViewHolder(binding: DoodleSquareBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.doodleImageView

        fun bind(bitmap: Bitmap) {
            imageView.setImageBitmap(null)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageBitmap(bitmap)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoodleAdapter.DoodleViewHolder {
        val binding = DoodleSquareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoodleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoodleAdapter.DoodleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item.bitmap)
    }
}

object MyDoodleDiffCallback : DiffUtil.ItemCallback<DoodleImage>() {
    override fun areItemsTheSame(oldItem: DoodleImage, newItem: DoodleImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DoodleImage, newItem: DoodleImage): Boolean {
        return oldItem == newItem
    }
}