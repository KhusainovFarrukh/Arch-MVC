package kh.farrukh.arch_mvc.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kh.farrukh.arch_mvc.data.local.LocalDatabase
import kh.farrukh.arch_mvc.data.local.MovieDao
import kh.farrukh.arch_mvc.data.remote.MoviesApi
import kh.farrukh.arch_mvc.data.remote.RetrofitClient
import javax.inject.Singleton

/**
 *Created by farrukh_kh on 4/7/22 11:47 PM
 *kh.farrukh.arch_mvc.di.modules
 **/
@[Module InstallIn(SingletonComponent::class)]
object AppModule {

    @[Provides Singleton]
    fun provideMoviesApi(): MoviesApi = RetrofitClient.moviesApi

    @[Provides Singleton]
    fun provideMovieDao(localDatabase: LocalDatabase): MovieDao = localDatabase.movieDao()

    @[Provides Singleton]
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase =
        Room.databaseBuilder(context, LocalDatabase::class.java, LocalDatabase.DB_NAME)
            .allowMainThreadQueries()
            .build()
}