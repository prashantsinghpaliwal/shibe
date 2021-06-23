package com.prashant.shibe.data.local

import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "shibe_table")
data class ShibeLocal(

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String? = "",

    @NonNull
    @PrimaryKey(autoGenerate = true)
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
