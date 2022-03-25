package com.example.photos.step3.gallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photos.databinding.GallerySquareBinding
import com.example.photos.step3.model.Image

class GalleryAdapter : ListAdapter<Image, GalleryAdapter.GalleryViewHolder>(MyDiffCallback) {

    class GalleryViewHolder(binding: GallerySquareBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.galleryImageView

        fun bind(path: String) {
            imageView.setImageBitmap(null)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageBitmap(decodeSampledBitmapFromPath(path))
        }

        private fun decodeSampledBitmapFromPath(
            path: String,
            reqWidth: Int = 100,
            reqHeight: Int = 100
        ): Bitmap {
            return BitmapFactory.Options().run {
                inJustDecodeBounds = true
                BitmapFactory.decodeFile(path, this)

                inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

                inJustDecodeBounds = false
                BitmapFactory.decodeFile(path, this)
            }
        }

        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val (height: Int, width: Int) = options.run { outHeight to outWidth }
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val halfHeight: Int = height / 2
                val halfWidth: Int = width / 2

                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }

            return inSampleSize
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding =
            GallerySquareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item.path)
    }
}

object MyDiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

}