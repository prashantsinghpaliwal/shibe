package com.prashant.shibe.common

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.util.*


//@BindingAdapter(value = ["setImageUrl"])
//fun bindImageUrl(view: ImageView, url: String?) {
//    if (url != null && url.isNotBlank()) {
//        Glide.with(view.context)
//            .load(url)
//            .fitCenter()
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .listener(object : RequestListener<Drawable> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return true
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//            })
//            .into(view)
//    }
//}
//
//@BindingAdapter(value = ["setRoundedImageUrl"])
//fun bindRoundedImageUrl(view: ImageView, url: String?) {
//    if (url != null && url.isNotBlank()) {
//        Glide.with(view.context)
//            .load(url)
//            .transition(withCrossFade())
//            .apply(RequestOptions().transform(RoundedCorners(50)))
//            .into(view)
//    }
//}

fun getRandomColor(): Int {
    val rand = Random()
    val randomColor: Int
    val r = rand.nextInt(256)
    val g = rand.nextInt(256)
    val b = rand.nextInt(256)
    randomColor = Color.argb((255 * 0.2).toInt(), r, g, b)
    return randomColor
}