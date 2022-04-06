package kh.farrukh.arch_mvc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kh.farrukh.arch_mvc.data.Movie
import kotlinx.coroutines.flow.Flow

/**
 *Created by farrukh_kh on 4/3/22 4:08 PM
 *kh.farrukh.arch_mvc.model
 **/
@Dao
interface MovieDao {

    @get:Query("SELECT * FROM movie_table")
    val all: Flow<List<Movie>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(movie: Movie)

    @Query("DELETE FROM movie_table WHERE id = :id")
    suspend fun delete(id: Int?)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(movie: Movie)
}