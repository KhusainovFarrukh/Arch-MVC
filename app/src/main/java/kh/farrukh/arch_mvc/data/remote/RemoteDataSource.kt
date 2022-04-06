package kh.farrukh.arch_mvc.data.remote

import kh.farrukh.arch_mvc.BuildConfig
import kh.farrukh.arch_mvc.utils.toResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

/**
 *Created by farrukh_kh on 4/3/22 4:14 PM
 *kh.farrukh.arch_mvc.model
 **/
class RemoteDataSource(
    private val moviesApi: MoviesApi,
    private val ioDispatcher: CoroutineContext
) {

    fun searchResults(query: String): Flow<Result<SearchMovieResponse>> = flow {
        try {
            emit(moviesApi.searchMovie(BuildConfig.TMDB_API_KEY, query).toResult())
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(ioDispatcher)
}