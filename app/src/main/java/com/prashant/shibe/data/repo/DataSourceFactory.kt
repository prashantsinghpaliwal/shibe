package com.prashant.shibe.data.repo
//
//import androidx.lifecycle.MutableLiveData
//import androidx.paging.DataSource
//import com.prashant.shibe.common.Resource
//import com.prashant.shibe.data.local.ShibeLocal
//import com.prashant.shibe.network.ShibeService
//
//
//class DataSourceFactory(private val remoteDataSource: ShibeService
//) :
//    DataSource.Factory<Long, ShibeLocal>() {
//
//    private var pagedDataSource: PagedDataSource? = null
//    private var mutableLiveData = MutableLiveData<PagedDataSource>()
//    private var networkState = MutableLiveData<Resource<Any>>()
//
//    init {
//        mutableLiveData = MutableLiveData<PagedDataSource>()
//    }
//
//    override fun create(): DataSource<Long, ShibeLocal>? {
//        pagedDataSource = PagedDataSource(remoteDataSource)
//        mutableLiveData.postValue(pagedDataSource)
//        networkState.postValue(Resource.loading())
//        return pagedDataSource
//    }
//
//    fun getMutableLiveData(): MutableLiveData<PagedDataSource> {
//        return mutableLiveData
//    }
//
//    fun getNetworkState(): MutableLiveData<Resource<Any>> {
//        return pagedDataSource?.getNetworkState() ?: networkState
//    }
//}
