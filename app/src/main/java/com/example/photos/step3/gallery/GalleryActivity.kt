package com.example.photos.step3.gallery

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photos.R
import com.example.photos.step3.model.Image

class GalleryActivity : AppCompatActivity() {

    private lateinit var recyclerGallery: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        recyclerGallery = findViewById(R.id.recycler_gallery)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            0
        )

        val imageList = getAllImagePathInStorage()
        val galleryAdapter = GalleryAdapter(contentResolver, imageList)
        recyclerGallery.adapter = galleryAdapter
        recyclerGallery.layoutManager = GridLayoutManager(this, 4)
        galleryAdapter.submitList(imageList)
    }

    private fun getAllImagePathInStorage(): List<Image> {
        val imageList = mutableListOf<Image>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val query = contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )

        var index = 0
        query.use { cursor ->
            cursor?.let {
                while (cursor.moveToNext()) {
                    val absolutePathOfImage =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
                    imageList.add(Image(++index, absolutePathOfImage))
                }
            }
        }

        return imageList.toList()
    }
}