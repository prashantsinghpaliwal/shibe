package com.prashant.shibe.data.paging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.bigsteptech.deazzle.data.local.ShibeDao
import com.prashant.shibe.common.Const
import com.prashant.shibe.data.local.ShibeLocal
import com.prashant.shibe.network.NetworkHelper
import com.prashant.shibe.network.ShibeService
import javax.inject.Inject

class ShibeRepository @Inject constructor(
    remoteDataSource: ShibeService,
    localDataSource: ShibeDao,
    private val networkHelper: NetworkHelper
) {

    private val dataSourceFactory = DataSourceFactory(remoteDataSource, localDataSource)
    private var liveList: LiveData<PagedList<ShibeLocal>> = MutableLiveData()

    init {
        initList()
    }

    private fun initList() {

        val status = if (networkHelper.isNetworkConnected())
            Const.ONLINE else Const.OFFLINE

        Log.v("PagingLogs", "requesting data from -> $status")
        this.liveList = dataSourceFactory.getLivePagedData(status)

    }

    fun getPagedData() = liveList

    fun getLoaderState() = dataSourceFactory.getLoaderState()

}