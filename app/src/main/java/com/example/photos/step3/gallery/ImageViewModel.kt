package com.example.photos.step3.gallery

import android.app.Application
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photos.step3.model.DoodleImage
import com.example.photos.step3.model.JsonDoodle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.InputStream
import java.net.URL
import kotlin.system.measureTimeMillis

class ImageViewModel(application: Application) : AndroidViewModel(application) {
    private val jsonDoodleList = mutableListOf<JsonDoodle>()

    private val list = mutableListOf<DoodleImage>()
    private val _imageList = MutableLiveData<List<DoodleImage>>()
    val imageList: LiveData<List<DoodleImage>> = _imageList

    init {
        _imageList.value = list
    }

    private fun getJsonDoodle() {
        val assetManager = getApplication<Application>().resources.assets

        try {
            val json = assetManager.open("json/doodle.json").reader().readText()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                val titleValue = jsonObject.getString("title")
                val imageValue = jsonObject.getString("image")
                val dateValue = jsonObject.getString("date")
                jsonDoodleList.add(JsonDoodle(titleValue, imageValue, dateValue))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getImage() {
        try {
            getJsonDoodle()
            jsonDoodleList.forEach {
                val image = it.image
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val url = URL(image)
                        val stream: InputStream?
                        val time = measureTimeMillis {
                            stream = url.openStream()
                        }
                        Log.d("time", time.toString())
                        val doodleImage =
                            DoodleImage(it.title.toInt(), BitmapFactory.decodeStream(stream))
                        list.add(doodleImage)
                        _imageList.postValue(list)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

