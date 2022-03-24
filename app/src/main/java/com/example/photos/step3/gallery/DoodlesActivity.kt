package com.example.photos.step3.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.photos.databinding.ActivityDoodlesBinding

class DoodlesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDoodlesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}