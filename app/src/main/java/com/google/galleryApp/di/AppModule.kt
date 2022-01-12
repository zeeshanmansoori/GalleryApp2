package com.google.galleryApp.di

import com.google.galleryApp.data.repository.PhotoRepositoryImpl
import com.google.galleryApp.data.repository.RetrofitApi
import com.google.galleryApp.domain.repository.PhotosRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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


    @Provides
    @Singleton
    fun provideGsonBuilder(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(RetrofitApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))


    @Provides
    @Singleton
    fun provideRetrofitApi(builder: Retrofit.Builder): RetrofitApi =
        builder.build().create(RetrofitApi::class.java)


    @Provides
    @Singleton
    fun providePhotoRepository(retrofitApi: RetrofitApi): PhotosRepository =
        PhotoRepositoryImpl(retrofitApi)


}