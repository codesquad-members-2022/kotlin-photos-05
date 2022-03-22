package com.example.photos

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.photos.databinding.ActivityPhotoBinding
import com.google.android.material.snackbar.Snackbar

class PhotoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPhotoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val image = findViewById<ImageView>(R.id.activity_photo_imageview)
        image.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24)

        val button = findViewById<Button>(R.id.activity_photo_button)
        button.setOnClickListener { requestPermissionLauncher.launch(REQUIRED_PERMISSIONS) }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.entries.forEach { permission ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when {
                    permission.value -> {
                        Snackbar.make(binding.root, "권한이 허용되었습니다", Snackbar.LENGTH_SHORT).show()
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

    // 권한 허용 두번 거부시 환경설정으로 이동하게 하는 메서드
    private fun openSetting() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.fromParts("package", packageName, null)
        }.run(::startActivity)
    }

    // REQUIRED_PERMISSIONS 중 하나라도 권한이 부여되지 않은 경우 false 를 반환하는 함수
    private fun isAllPermissionGranted(): Boolean = REQUIRED_PERMISSIONS.all { permission ->
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}