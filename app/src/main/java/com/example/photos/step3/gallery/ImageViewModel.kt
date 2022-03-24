package com.example.photos.step3.gallery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.photos.step3.model.JsonDoodle
import org.json.JSONArray

class ImageViewModel(application: Application): AndroidViewModel(application) {
    val jsonDoodleList = mutableListOf<JsonDoodle>()

    fun getJsonDoodle() {
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
}

