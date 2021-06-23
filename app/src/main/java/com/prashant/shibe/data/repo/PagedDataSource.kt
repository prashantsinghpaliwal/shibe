package com.prashant.shibe.data.repo
//
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import androidx.paging.PageKeyedDataSource
//import com.prashant.shibe.common.Resource
//import com.prashant.shibe.data.local.ShibeLocal
//import com.prashant.shibe.network.ShibeService
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
//
//class PagedDataSource(
//    private val remoteDataSource: ShibeService
//) : PageKeyedDataSource<Long, ShibeLocal>() {
//
//    private var networkState = MutableLiveData<Resource<Any>>()
//
//    fun getNetworkState(): MutableLiveData<Resource<Any>> {
//        return networkState
//    }
//
//    override fun loadInitial(
//        params: LoadInitialParams<Long>,
//        callback: LoadInitialCallback<Long, ShibeLocal>
//    ) {
//        networkState.postValue(Resource.loading())
//
//        remoteDataSource.getShibes(1, 8, false)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                it?.let {
//                    Log.v("PagingLogs", "data ${it}")
//                    networkState.postValue(Resource.success(it))
//                    callback.onResult(it, null, 2)
//
//                }
//            }, {
//                networkState.postValue(Resource.error(it))
//                Log.v("PagingLogs", "message ${it.localizedMessage}")
//            })
//
//    }
//
//    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, ShibeLocal>) {
//
//    }
//
//    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, ShibeLocal>) {
//        networkState.postValue(Resource.loading())
//
//        remoteDataSource.getShibes((params.key).toInt(), 8, true)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                it?.let {
//                    Log.v("PagingLogs", "data for page ${params.key} -> ${it}")
//                    networkState.postValue(Resource.success(it))
//                    callback.onResult(it, params.key + 1)
//                }
//            }, {
//                networkState.postValue(Resource.error(it))
//                Log.v("PagingLogs", "message ${it.localizedMessage}")
//            })
//
//    }
//}