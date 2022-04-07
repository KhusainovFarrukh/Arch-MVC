package kh.farrukh.arch_mvc.data.remote

import kh.farrukh.arch_mvc.BuildConfig
import kh.farrukh.arch_mvc.di.modules.IoDispatcher
import kh.farrukh.arch_mvc.utils.toResult
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/**
 *Created by farrukh_kh on 4/3/22 4:14 PM
 *kh.farrukh.arch_mvc.model
 **/
@Singleton
class RemoteDataSource @Inject constructor(
    private val moviesApi: MoviesApi,
    @IoDispatcher private val ioDispatcher: CoroutineContext
) {

    suspend fun searchResults(query: String): Result<SearchMovieResponse> =
        withContext(ioDispatcher) {
            try {
                moviesApi.searchMovie(BuildConfig.TMDB_API_KEY, query).toResult()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}