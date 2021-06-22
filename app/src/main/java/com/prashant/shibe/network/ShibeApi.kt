package com.prashant.shibe.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ShibeApi {

    @GET("api/shibes")
    fun getShibes(
        @Query("page") page: Int,
        @Query("count") count: Int
    ): Single<List<String>>

}