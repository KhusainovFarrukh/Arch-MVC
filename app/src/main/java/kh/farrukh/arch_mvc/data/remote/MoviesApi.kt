package kh.farrukh.arch_mvc.data.remote

import io.reactivex.rxjava3.core.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *Created by farrukh_kh on 4/3/22 3:28 PM
 *kh.farrukh.arch_mvc.network
 **/
interface MoviesApi {

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("query") q: String): Flowable<Response<SearchMovieResponse>>
}