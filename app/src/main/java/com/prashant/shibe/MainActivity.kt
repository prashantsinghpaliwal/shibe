package com.prashant.shibe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.prashant.imageloader.ImageLoader

class MainActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.image)

        ImageLoader.displayImage(imageView,"https://cdn.shibe.online/shibes/feedcc9b2735b82f936fae19ed88ed89d6562cd7.jpg")
    }
}