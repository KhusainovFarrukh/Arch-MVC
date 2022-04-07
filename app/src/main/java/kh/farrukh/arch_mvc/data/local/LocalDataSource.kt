package kh.farrukh.arch_mvc.data.local

import kh.farrukh.arch_mvc.data.Movie
import kh.farrukh.arch_mvc.di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
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

    private val ioScope by lazy { CoroutineScope(ioDispatcher) }
    val allMovies: Flow<List<Movie>> by lazy { movieDao.all }

    fun insert(movie: Movie) = ioScope.launch {
        movieDao.insert(movie)
    }

    fun delete(movie: Movie) = ioScope.launch {
        movieDao.delete(movie.id)
    }

    fun update(movie: Movie) = ioScope.launch {
        movieDao.update(movie)
    }
}