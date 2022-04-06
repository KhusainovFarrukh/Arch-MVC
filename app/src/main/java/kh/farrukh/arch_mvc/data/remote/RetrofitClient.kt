package kh.farrukh.arch_mvc.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 *Created by farrukh_kh on 4/3/22 3:27 PM
 *kh.farrukh.arch_mvc.network
 **/
object RetrofitClient {
    private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val TMDB_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"

    val moviesApi: MoviesApi = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()
}