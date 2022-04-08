package kh.farrukh.arch_mvc.data.local

import io.reactivex.rxjava3.core.Flowable
import kh.farrukh.arch_mvc.data.Movie
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

/**
 *Created by farrukh_kh on 4/3/22 4:14 PM
 *kh.farrukh.arch_mvc.model
 **/
@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
) {

    val allMovies: Flowable<List<Movie>> by lazy { movieDao.all }

    fun insert(movie: Movie) = thread {
        movieDao.insert(movie)
    }

    fun delete(movie: Movie) = thread {
        movieDao.delete(movie.id)
    }

    fun update(movie: Movie) = thread {
        movieDao.update(movie)
    }
}