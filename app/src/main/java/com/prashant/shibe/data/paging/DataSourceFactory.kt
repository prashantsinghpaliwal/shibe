package com.prashant.shibe.data.paging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bigsteptech.deazzle.data.local.ShibeDao
import com.prashant.shibe.common.Const
import com.prashant.shibe.common.LoaderState
import com.prashant.shibe.data.local.ShibeLocal
import com.prashant.shibe.network.ShibeService

class DataSourceFactory(
    private val remoteDataSource: ShibeService,
    private val localDataSource: ShibeDao
) {


    private val offlineDataSourceFactory: DataSource.Factory<Int, ShibeLocal> =
        localDataSource.getPagedData()
    private var liveLoaderState: MutableLiveData<LoaderState> = MutableLiveData(LoaderState.DONE)
    private val config: PagedList.Config

    init {
        config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
    }


    private fun offlinePagedListBuilder(): LivePagedListBuilder<Int, ShibeLocal> {
        return LivePagedListBuilder(offlineDataSourceFactory, config)
    }


    private fun onlinePagedListBuilder(): LivePagedListBuilder<Long, ShibeLocal> {
        val dataSourceFactory = object : DataSource.Factory<Long, ShibeLocal>() {
            override fun create(): DataSource<Long, ShibeLocal> {
                return DataSource(remoteDataSource, localDataSource, liveLoaderState)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }


    fun getLivePagedData(type: Int): LiveData<PagedList<ShibeLocal>> {

        when (type) {

            Const.OFFLINE -> {
                Log.e("NewsFactory", "NewsFactory->OfflineDataSet")
                return offlinePagedListBuilder().build()
            }

            Const.ONLINE -> {
                Log.e("NewsFactory", "NewsFactory->OnlineDataSet")
                return onlinePagedListBuilder().build()
            }

            else -> {
                Log.e("NewsFactory", "NewsFactory->OtherDataSet")
                return MutableLiveData()
            }
        }

    }

    fun getLoaderState() = liveLoaderState

}