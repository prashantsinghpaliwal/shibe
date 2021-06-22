package com.prashant.shibe.network


import com.prashant.shibe.data.local.ShibeLocal
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface ShibeService {

    fun getShibes(page: Int, count: Int, loadMoreRequest: Boolean): Observable<List<ShibeLocal>>

    class Impl @Inject constructor(
        private val issuesApi: ShibeApi
    ) : ShibeService {

        override fun getShibes(page: Int, count: Int, loadMoreRequest: Boolean) =
            issuesApi.getShibes(page, count).map {
                it.mapIndexed { index, s -> ShibeLocal(s, page * index) }.toList()
            }.toObservable()
                .subscribeOn(Schedulers.io())

    }

}