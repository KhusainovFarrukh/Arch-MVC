package kh.farrukh.arch_mvc

import kh.farrukh.arch_mvc.data.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 *Created by farrukh_kh on 4/7/22 11:01 PM
 *kh.farrukh.arch_mvc
 **/
class MovieTest {

    @Test
    fun testGetReleaseDateFromStringFormattedDate() {
        val movie = Movie(title = "Test", releaseDate = "2022-07-07")
        assertEquals("2022", movie.getReleaseYearFromDate())
    }

    @Test
    fun testGetReleaseDateFromYear() {
        val movie = Movie(title = "Test", releaseDate = "2022")
        assertEquals("2022", movie.getReleaseYearFromDate())
    }

    @Test
    fun testGetReleaseDateFromDateEdgeCaseEmpty() {
        val movie = Movie(title = "Test", releaseDate = "")
        assertEquals("", movie.getReleaseYearFromDate())
    }

    @Test
    fun testGetReleaseDateFromDateEdgeCaseNull() {
        val movie = Movie(title = "Test", releaseDate = null)
        assertEquals("", movie.getReleaseYearFromDate())
    }
}