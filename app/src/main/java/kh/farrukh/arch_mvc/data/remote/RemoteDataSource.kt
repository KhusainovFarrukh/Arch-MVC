package kh.farrukh.arch_mvc.data.remote

import io.reactivex.rxjava3.core.Flowable
import kh.farrukh.arch_mvc.BuildConfig
import kh.farrukh.arch_mvc.utils.toResult

/**
 *Created by farrukh_kh on 4/3/22 4:14 PM
 *kh.farrukh.arch_mvc.model
 **/
class RemoteDataSource(private val moviesApi: MoviesApi) {

    fun searchResults(query: String): Flowable<Result<SearchMovieResponse>> {
        return moviesApi.searchMovie(BuildConfig.TMDB_API_KEY, query)
            .map { response -> response.toResult() }
    }
}