package kh.farrukh.arch_mvc.data.local

import kh.farrukh.arch_mvc.data.Movie
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 *Created by farrukh_kh on 4/3/22 4:14 PM
 *kh.farrukh.arch_mvc.model
 **/
class LocalDataSource(
    private val movieDao: MovieDao,
    private val ioDispatcher: CoroutineContext
) {

    suspend fun getAll() = withContext(ioDispatcher) {
        movieDao.getAll()
    }

    suspend fun insert(movie: Movie) = withContext(ioDispatcher) {
        movieDao.insert(movie)
    }

    suspend fun delete(movie: Movie) = withContext(ioDispatcher) {
        movieDao.delete(movie.id)
    }

    suspend fun update(movie: Movie) = withContext(ioDispatcher) {
        movieDao.update(movie)
    }
}