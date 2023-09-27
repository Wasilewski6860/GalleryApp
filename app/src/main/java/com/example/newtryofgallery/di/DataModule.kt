package com.example.newtryofgallery.di

import com.example.newtryofgallery.data.db.PicturesDatabase
import com.example.newtryofgallery.data.db.PicturesStorage
import com.example.newtryofgallery.data.db.impl.PictureStorageImpl
import com.example.newtryofgallery.data.repository.PicturesRepositoryImpl
import com.example.newtryofgallery.domain.repositories.PicturesRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<PicturesStorage>{ PictureStorageImpl(picturesDatabase = get() )}
    single<PicturesRepository>{ PicturesRepositoryImpl(picturesStorage =  get()) }
    single<PicturesDatabase> { PicturesDatabase.getInstance(context = get()) }
//    single<CatsStorage> { CatStorageImpl(catsDatabase = get(), catApi = get()) }
//
//
//    single<CatsRepository> { CatRepositoryImpl(catsStorage = get()) }
//
//    single<CatsApi> {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL).client(
//                OkHttpClient.Builder()
//                .build())
//            .addConverterFactory(GsonConverterFactory.create()).build().create(CatsApi::class.java)
//    }
//
//    single<CatsDatabase> { CatsDatabase.getDataBase(context = get()) }
}