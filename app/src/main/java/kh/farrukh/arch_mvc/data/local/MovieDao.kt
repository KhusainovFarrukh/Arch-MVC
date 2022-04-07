package kh.farrukh.arch_mvc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Flowable
import kh.farrukh.arch_mvc.data.Movie
import kotlinx.coroutines.flow.Flow

/**
 *Created by farrukh_kh on 4/3/22 4:08 PM
 *kh.farrukh.arch_mvc.model
 **/
@Dao
interface MovieDao {

    @get:Query("SELECT * FROM movie_table")
    val all: Flowable<List<Movie>>

    @Insert(onConflict = REPLACE)
    fun insert(movie: Movie)

    @Query("DELETE FROM movie_table WHERE id = :id")
    fun delete(id: Int?)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Update
    fun update(movie: Movie)
}