package kh.farrukh.arch_mvc.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kh.farrukh.arch_mvc.data.Movie

/**
 *Created by farrukh_kh on 4/3/22 4:11 PM
 *kh.farrukh.arch_mvc.model
 **/

@Database(entities = [Movie::class], version = 1)
@TypeConverters(kh.farrukh.arch_mvc.data.local.TypeConverters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        private const val DB_NAME = "movie_database"

        fun getInstance(application: Application): LocalDatabase =
            Room.databaseBuilder(application, LocalDatabase::class.java, DB_NAME)
                .allowMainThreadQueries()
                .build()
    }
}