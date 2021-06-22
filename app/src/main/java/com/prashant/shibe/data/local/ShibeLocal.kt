package com.prashant.shibe.data.local

import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey



data class ShibeLocal(

    var imageUrl: String? = "",

    var id: Int = 0

){

    companion object {
        val CALLBACK: DiffUtil.ItemCallback<ShibeLocal> =
            object : DiffUtil.ItemCallback<ShibeLocal>() {
                override fun areItemsTheSame(
                    @NonNull items: ShibeLocal,
                    @NonNull t1: ShibeLocal
                ): Boolean {
                    return items.id === t1.id
                }

                override fun areContentsTheSame(
                    @NonNull items: ShibeLocal,
                    @NonNull t1: ShibeLocal
                ): Boolean {
                    return true
                }
            }
    }

}
