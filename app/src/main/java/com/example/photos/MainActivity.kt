package com.example.photos

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photos.databinding.ActivityMainBinding
import com.example.photos.step3.gallery.GalleryActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val colorList: MutableList<Int> = mutableListOf()
        for (i in 1..40) {
            colorList.add(Color.rgb(Random.nextInt(0, 255), Random.nextInt(0, 255), Random.nextInt(0, 255)))
        }

        binding.recycler.adapter = RecyclerAdapter(colorList)
        binding.recycler.layoutManager = GridLayoutManager(this, 4)
        binding.recycler.clipToPadding = false
        binding.recycler.clipChildren = false
        binding.recycler.addItemDecoration(ItemDecoration())

        binding.buttonPermission.setOnClickListener {
            val intent = Intent(this, PhotoActivity::class.java)
            startActivity(intent)
        }

        binding.buttonGallery.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }
    }
}