package com.prashant.shibe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShibeDao {


    @Query("SELECT * FROM shibe")
    fun getAll(): Flow<List<ShibeLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(profiles: List<ShibeLocal>)

    @Query("DELETE FROM shibe")
    fun deleteAll()

}