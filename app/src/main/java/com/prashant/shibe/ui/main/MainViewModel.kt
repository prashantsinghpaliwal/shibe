//package com.prashant.shibe.ui.main
//
//import androidx.hilt.lifecycle.ViewModelInject
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.paging.LivePagedListBuilder
//import androidx.paging.PagedList
//import com.prashant.shibe.common.Resource
//import com.prashant.shibe.data.local.ShibeLocal
//import com.prashant.shibe.data.repo.DataSourceFactory
//import com.prashant.shibe.data.repo.PagedDataSource
//import com.prashant.shibe.network.ShibeService
//import java.util.concurrent.Executor
//import java.util.concurrent.Executors
//
//
//class MainViewModel @ViewModelInject constructor(
//    shibeService: ShibeService
//) :
//    ViewModel() {
//
//    private var photoDataSourceFactory: DataSourceFactory? = null
//    private var dataSourceMutableLiveData: MutableLiveData<PagedDataSource>? = null
//    private var executor: Executor? = null
//    private var pagedListLiveData: LiveData<PagedList<ShibeLocal>>? = null
//    private var networkState: MutableLiveData<Resource<Any>>? = null
//
//
//    init {
//        photoDataSourceFactory = DataSourceFactory(shibeService)
//        dataSourceMutableLiveData = photoDataSourceFactory?.getMutableLiveData()
//
//        val config = PagedList.Config.Builder()
//            .setEnablePlaceholders(true)
//            .setInitialLoadSizeHint(10)
//            .setPageSize(20)
//            .setPrefetchDistance(4)
//            .build()
//
//        executor = Executors.newFixedThreadPool(5)
//        pagedListLiveData = LivePagedListBuilder(photoDataSourceFactory!!, config)
//            .setFetchExecutor(executor!!)
//            .build()
//
//        networkState = photoDataSourceFactory?.getNetworkState()
//
//    }
//
//    fun getPagedData(): LiveData<PagedList<ShibeLocal>>? {
//        return pagedListLiveData
//    }
//
//    fun getNetworkState(): MutableLiveData<Resource<Any>> {
//        return networkState!!
//    }
//}