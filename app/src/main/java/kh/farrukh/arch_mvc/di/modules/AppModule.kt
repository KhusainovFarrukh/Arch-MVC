package kh.farrukh.arch_mvc.di.modules

import kh.farrukh.arch_mvc.data.local.LocalDataSource
import kh.farrukh.arch_mvc.data.local.LocalDatabase
import kh.farrukh.arch_mvc.data.remote.RemoteDataSource
import kh.farrukh.arch_mvc.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

/**
 *Created by farrukh_kh on 4/8/22 4:10 PM
 *kh.farrukh.arch_mvc.di.modules
 **/
val appModule = module {

    single { LocalDataSource(LocalDatabase.getInstance(get()).movieDao(), Dispatchers.IO) }

    single { RemoteDataSource(RetrofitClient.moviesApi, Dispatchers.IO) }
}