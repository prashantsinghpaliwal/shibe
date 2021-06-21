package com.prashant.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.LruCache
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.Collections.synchronizedMap

class ImageLoader(context: Context) {

    private val maxCacheSize: Int = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 8
    private val memoryCache: LruCache<String, Bitmap>

    private val imageViewMap = synchronizedMap(WeakHashMap<ImageView, String>())
    private var placeHolder: Drawable? = null

    init {
        memoryCache = object : LruCache<String, Bitmap>(maxCacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                // The cache size will be measured in kilobytes rather than number of items.
                return bitmap.byteCount / 1024
            }
        }

        val metrics = context.resources.displayMetrics
        screenWidth = metrics.widthPixels
        screenHeight = metrics.heightPixels
    }

    companion object {

        private var INSTANCE: ImageLoader? = null

        internal var screenWidth = 0
        internal var screenHeight = 0

        @Synchronized
        fun with(context: Context): ImageLoader {

            require(context != null) {
                "ImageLoader:with - Context should not be null."
            }

            return INSTANCE ?: ImageLoader(context).also {
                INSTANCE = it
            }

        }
    }

    fun load(imageView: ImageView, imageUrl: String) {

        require(imageView != null) {
            "ImageLoader:load - ImageView should not be null."
        }

        require(imageUrl != null && imageUrl.isNotEmpty()) {
            "ImageLoader:load - Image Url should not be empty"
        }

        imageView.setImageResource(0)
        imageViewMap[imageView] = imageUrl

        placeHolder?.let {
            imageView.setImageDrawable(placeHolder)
        }

        val bitmap = checkImageInCache(imageUrl)
        bitmap?.let {
            loadImageIntoImageView(imageView, it, imageUrl)
        } ?: run {
            imageView.tag = imageUrl
            CoroutineScope(Dispatchers.IO).launch {
                val bitmap = downloadImage(imageUrl)
                withContext(Dispatchers.Main) {
                    if (bitmap != null) {
                        if (imageView.tag == imageUrl) {
                            updateImageView(imageView, bitmap, imageUrl)
                        }
                        memoryCache.put(imageUrl, bitmap)
                    }
                }
            }
        }

    }

    private fun updateImageView(imageView: ImageView, bitmap: Bitmap, imageUrl: String) {
        val scaledBitmap = Utils.scaleBitmapForLoad(bitmap, imageView.width, imageView.height)
        scaledBitmap?.let {
            if (!isImageViewReused(ImageRequest(imageUrl, imageView))) {
                imageView.post {
                    imageView.setImageBitmap(scaledBitmap)
                }
            }
        }

    }

    private fun isImageViewReused(imageRequest: ImageRequest): Boolean {
        val tag = imageViewMap[imageRequest.imageView]
        return tag == null || tag != imageRequest.imgUrl
    }

    @Synchronized
    private fun checkImageInCache(imageUrl: String): Bitmap? = memoryCache.get(imageUrl)

    @Synchronized
    private fun loadImageIntoImageView(imageView: ImageView, bitmap: Bitmap?, imageUrl: String) {

        require(bitmap != null) {
            "ImageLoader:loadImageIntoImageView - Bitmap should not be null"
        }

        val scaledBitmap = Utils.scaleBitmapForLoad(bitmap, imageView.width, imageView.height)
        scaledBitmap?.let {
            if (!isImageViewReused(ImageRequest(imageUrl, imageView)))
                imageView.setImageBitmap(scaledBitmap)
        }
    }

    inner class ImageRequest(var imgUrl: String, var imageView: ImageView)


    private fun downloadImage(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url = URL(url)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            bitmap = BitmapFactory.decodeStream(conn.inputStream)
            conn.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }

    fun placeHolder(placeHolder: Drawable): ImageLoader {
        this.placeHolder = placeHolder
        return INSTANCE!!
    }
}