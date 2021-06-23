package com.prashant.shibe.data.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.bigsteptech.deazzle.data.local.ShibeDao
import com.prashant.shibe.common.LoaderState
import com.prashant.shibe.data.local.ShibeLocal
import com.prashant.shibe.network.ShibeService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class DataSource(
    private val remoteDataSource: ShibeService,
    private val localDataSource: ShibeDao,
    private val liveLoaderState: MutableLiveData<LoaderState>
) : PageKeyedDataSource<Long, ShibeLocal>() {


    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, ShibeLocal>) {
        val pageNumber: Long = params.key
        val next: Long = pageNumber + 1

        remoteDataSource.getShibes(pageNumber.toInt(), 8, false)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let {
                    Log.v("PagingLogs", "data ${it}")
                    callback.onResult(it, next)

                }
            }, {
                Log.v("PagingLogs", "message ${it.localizedMessage}")
                liveLoaderState.postValue(LoaderState.ERROR)
            })

    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, ShibeLocal>) {

    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, ShibeLocal>
    ) {
        val pageNumber = 1L;
        val next: Long = pageNumber + 1;
        val prev: Long = pageNumber - 1

        liveLoaderState.postValue(LoaderState.LOADING)
        remoteDataSource.getShibes(1, 8, false)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let {
                    Log.v("PagingLogs", "data ${it}")
                    callback.onResult(it, prev, next)

                }
            }, {
                Log.v("PagingLogs", "message ${it.localizedMessage}")
                liveLoaderState.postValue(LoaderState.ERROR)
            })


    }
}