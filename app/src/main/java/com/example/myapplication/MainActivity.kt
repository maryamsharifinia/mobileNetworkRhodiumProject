package com.example.myapplication
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var mapImageView: ImageView
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapImageView = findViewById(R.id.mapImageView)
        val loadMapButton: Button = findViewById(R.id.loadMapButton)

        loadMapButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        mapImageView.setOnTouchListener { v, event ->
            val x = event.x
            val y = event.y
            // تبدیل مختصات پیکسل به مختصات نقشه و ذخیره در پایگاه داده
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri? = data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
            mapImageView.setImageBitmap(bitmap)
        }
    }
}


