package com.prashant.shibe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.prashant.imageloader.CustomImageLoader
import com.prashant.imageloader.ImageLoader

class MainActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.image)

        ImageLoader.with(this)
            .placeHolder(ContextCompat.getDrawable(this,R.drawable.ic_photo_24)!!)
            .load(imageView,
            "https://cdn.shibe.online/shibes/e056e6b3d0d17628d1771f8559b6cb629dc5f455.jpg")
    }
}