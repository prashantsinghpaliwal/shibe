package com.prashant.shibe.di

import android.content.Context
import com.prashant.shibe.BuildConfig
import com.prashant.shibe.data.local.AppDatabase
import com.prashant.shibe.data.local.ShibeDao
import com.prashant.shibe.data.repo.ShibeRepository
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
        api: ShibeApi,
        dao: ShibeDao
    ): ShibeService {
        return ShibeService.Impl(
            api, dao
        )
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)


    @Singleton
    @Provides
    fun provideshibeDao(db: AppDatabase) = db.shibeDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: ShibeService,
        localDataSource: ShibeDao
    ) = ShibeRepository(remoteDataSource, localDataSource)


}