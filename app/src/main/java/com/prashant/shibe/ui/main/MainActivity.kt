package com.prashant.shibe.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.prashant.imageloader.ImageLoader
import com.prashant.shibe.R
import com.prashant.shibe.common.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.image)

        setUpObservers()

//        ImageLoader.with(this)
//            .placeHolder(ContextCompat.getDrawable(this, R.drawable.ic_photo_24)!!)
//            .load(imageView,
//            "https://cdn.shibe.online/shibes/e056e6b3d0d17628d1771f8559b6cb629dc5f455.jpg")
    }


    private fun setUpObservers() {

        viewModel.getPagedData()?.observe(this, { pagedList ->

            pagedList?.let {

            }
//            
        })

        viewModel.getNetworkState().observe(this, {

            when (it.status) {

                Status.LOADING -> {
                    
                }

                Status.SUCCESS -> {
                    
                }

                Status.ERROR -> {
                    Toast.makeText(this, it.throwable?.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                    
                }

            }
        })

    }
}