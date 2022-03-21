package com.example.photos

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val colorList: MutableList<Int> = mutableListOf()
        for (i in 1..40) {
            colorList.add(Color.rgb(Random.nextInt(0, 255), Random.nextInt(0, 255), Random.nextInt(0, 255)))
        }

        val adapter = RecyclerAdapter(colorList)

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(this, 4)
        recycler.clipToPadding = false
        recycler.clipChildren = false
        recycler.addItemDecoration(ItemDecoration())
    }
}