package com.example.photos.step3.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photos.databinding.ActivityDoodlesBinding

class DoodlesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDoodlesBinding.inflate(layoutInflater)
    }

    private lateinit var imageViewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar = binding.doodlesTopAppToolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        imageViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                ImageViewModel::class.java
            )

        val doodleAdapter = DoodleAdapter()
        binding.doodlesRecyclerview.adapter = doodleAdapter
        binding.doodlesRecyclerview.layoutManager = GridLayoutManager(this, 4)

        imageViewModel.getImage()
        imageViewModel.imageList.observe(this) {
            doodleAdapter.submitList(it.toList())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}