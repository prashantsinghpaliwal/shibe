package com.prashant.shibe.common

import android.graphics.Color
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.prashant.imageloader.ImageLoader
import com.prashant.shibe.R
import java.util.*


@BindingAdapter(value = ["setRoundedImageUrl"])
fun bindRoundedImageUrl(view: ImageView, url: String?) {
    if (url != null && url.isNotBlank()) {
        ImageLoader.with(view.context)
            .placeHolder(ContextCompat.getDrawable(view.context, R.drawable.ic_photo_24)!!)
            .roundedCorners(50)
            .load(
                view,
                url
            )
    }
}