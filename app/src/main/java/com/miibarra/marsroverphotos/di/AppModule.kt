package com.miibarra.marsroverphotos.di

import com.miibarra.marsroverphotos.data.remote.RoverRetrofitService
import com.miibarra.marsroverphotos.data.repository.RoverRepositoryImpl
import com.miibarra.marsroverphotos.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoverRepository(
        api: RoverRetrofitService
    ) = RoverRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideRoverApi(): RoverRetrofitService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RoverRetrofitService::class.java)
    }
}