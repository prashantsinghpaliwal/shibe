package com.prashant.shibe.di

import android.content.Context
import com.prashant.shibe.BuildConfig
import com.prashant.shibe.network.ShibeApi
import com.prashant.shibe.network.ShibeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ShibeModule {

    @Singleton
    @Provides
    fun shibeApi(retrofit: Retrofit.Builder, okHttpClient: OkHttpClient): ShibeApi {
        return retrofit
            .baseUrl(BuildConfig.BASE_ENDPOINT)
            .client(okHttpClient)
            .build()
            .create(ShibeApi::class.java)
    }

    @Provides
    fun shibeService(
        api: ShibeApi
    ): ShibeService {
        return ShibeService.Impl(api)
    }

}