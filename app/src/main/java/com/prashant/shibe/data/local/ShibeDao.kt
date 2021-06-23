package com.bigsteptech.deazzle.data.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prashant.shibe.data.local.ShibeLocal

@Dao
interface ShibeDao {

    @Query("SELECT * FROM shibe_table")
    fun getPagedData() : DataSource.Factory<Int, ShibeLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ShibeLocal>)

    @Query("DELETE FROM shibe_table")
    fun deleteAll()

}