package com.example.photos.step3.gallery

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photos.R
import com.example.photos.step3.model.Image

class GalleryAdapter(
    private val contentResolver: ContentResolver,
    private val dataset: List<Image>
) : ListAdapter<Image, GalleryAdapter.GalleryViewHolder>(MyDiffCallback) {

    inner class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageView)

        fun bind(path: String) {
            imageView.setImageBitmap(null)
            // TODO 코루틴으로 변경
            val thread = Thread(Runnable() {
                val bitmap = decodeSampledBitmapFromPath(path)
                val handler = Handler(Looper.getMainLooper()).post(Runnable() {
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    imageView.setImageBitmap(bitmap)
                })
            })
            thread.start()
        }

        // https://developer.android.com/topic/performance/graphics/load-bitmap
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

        private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
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
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.color_square, parent, false)
        return GalleryViewHolder(layout)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(dataset[position].path)
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