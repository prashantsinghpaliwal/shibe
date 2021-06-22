package com.prashant.shibe.data.repo

import android.util.Log
import com.prashant.shibe.data.local.ShibeDao
import com.prashant.shibe.data.local.ShibeLocal
import com.prashant.shibe.network.ShibeService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShibeRepository @Inject constructor(
    private val remoteDataSource: ShibeService,
    private val localDataSource: ShibeDao
) {

    fun getShibes(page: Int) {
//        remoteDataSource.getShibes(page,20)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                it?.let {
//                    localDataSource.deleteAll()
//                    localDataSource.insertAll(it)
//                }
//            }, {
//                Log.v("CameraLogs", "${it}")
//            })
    }

    fun getCachedShibes(): Flow<List<ShibeLocal>> =
        localDataSource.getAll()

}