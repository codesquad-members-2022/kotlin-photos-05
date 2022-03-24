package com.example.photos.step3.gallery

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.Menu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photos.R
import com.example.photos.databinding.ActivityGalleryBinding
import com.example.photos.step3.model.Image
import com.google.android.material.snackbar.Snackbar

class GalleryActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityGalleryBinding.inflate(layoutInflater)
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    }

    private fun openSetting() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.fromParts("package", packageName, null)
        }.run(::startActivity)
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.entries.forEach { permission ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when {
                    permission.value -> {
                        Snackbar.make(binding.root, "권한이 허용되었습니다", Snackbar.LENGTH_SHORT).show()
                        val imageList = getAllImagePathInStorage()
                        val galleryAdapter = GalleryAdapter(contentResolver, imageList)
                        binding.recyclerGallery.adapter = galleryAdapter
                        binding.recyclerGallery.layoutManager = GridLayoutManager(this, 4)
                        galleryAdapter.submitList(imageList)
                    }
                    shouldShowRequestPermissionRationale(permission.key) -> {
                        Snackbar.make(binding.root, "앱을 이용하려면 권한이 필요합니다", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {
                        Snackbar.make(binding.root, "권한이 없습니다.", Snackbar.LENGTH_SHORT).show()
                        openSetting()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        val plusButton = binding.galleryTopAppToolbar.menu[0]
        plusButton.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.plus_icon -> {
                    val intent = Intent(this, DoodlesActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.gallery_appbar_menu, menu)
        return true
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