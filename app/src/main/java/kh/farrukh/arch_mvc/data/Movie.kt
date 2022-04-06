package kh.farrukh.arch_mvc.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 *Created by farrukh_kh on 4/3/22 4:04 PM
 *kh.farrukh.arch_mvc.model
 **/
@Entity(tableName = "movie_table")
data class Movie(
    @SerializedName("vote_count")
    var voteCount: Int? = null,
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("video")
    var video: Boolean? = null,
    @SerializedName("vote_average")
    var voteAverage: Float? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("popularity")
    var popularity: Float? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("genre_ids")
    var genreIds: List<Int>? = null,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("adult")
    var adult: Boolean? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    var watched: Boolean = false
) {

    fun getReleaseYearFromDate(): String? {
        return releaseDate?.split("-")?.get(0)
    }
}