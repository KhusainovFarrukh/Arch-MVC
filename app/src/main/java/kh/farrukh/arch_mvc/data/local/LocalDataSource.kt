package kh.farrukh.arch_mvc.data.local

import kh.farrukh.arch_mvc.data.Movie
import kh.farrukh.arch_mvc.di.modules.IoDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/**
 *Created by farrukh_kh on 4/3/22 4:14 PM
 *kh.farrukh.arch_mvc.model
 **/
@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    @IoDispatcher private val ioDispatcher: CoroutineContext
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