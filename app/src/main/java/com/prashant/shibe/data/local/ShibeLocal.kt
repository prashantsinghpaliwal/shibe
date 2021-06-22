package com.prashant.shibe.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "shibe")
data class ShibeLocal(

    var imageUrl: String? = "",

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

)
