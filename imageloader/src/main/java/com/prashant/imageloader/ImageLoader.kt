package com.prashant.imageloader

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.LruCache
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.Collections.synchronizedMap


class ImageLoader(private val context: Context) {

    private val maxCacheSize: Int = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 8
    private val memoryCache: LruCache<String, Bitmap>

    private val imageViewMap = synchronizedMap(WeakHashMap<ImageView, String>())
    private var placeHolder: Drawable? = null
    private var roundedCorners: Int? = null

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
                   roundedCorners?.let {
                       setRoundedCorners(imageView)
                   }
                    imageView.setImageBitmap(scaledBitmap)
                }
            }
        }

    }

    private fun setRoundedCorners(imageView: ImageView) {
        val randomColor = getRandomColor()
        val drawable = ContextCompat.getDrawable(context, R.drawable.cardview_bg)
        drawable?.mutate()?.setColorFilter(randomColor, PorterDuff.Mode.SRC_ATOP)
        imageView.background = drawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val provider = object : ViewOutlineProvider() {
//                override fun getOutline(view: View?, outline: Outline?) {
//                    val margin = context.resources.getDimension(R.dimen.margin_15dp).toInt()
//                    outline?.setRoundRect(0, 0, view!!.width,
//                        (view.height + margin), margin.toFloat())
//                }
//            }
//            imageView.outlineProvider = provider
            imageView.clipToOutline = true
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

    fun roundedCorners(roundedCorners: Int): ImageLoader {
        this.roundedCorners = roundedCorners
        return INSTANCE!!
    }

   private fun getRandomColor(): Int {
        val rand = Random()
        val randomColor: Int
        val r = rand.nextInt(256)
        val g = rand.nextInt(256)
        val b = rand.nextInt(256)
        randomColor = Color.argb((255 * 0.2).toInt(), r, g, b)
        return randomColor
    }
}