package kh.farrukh.arch_mvc.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kh.farrukh.arch_mvc.data.Movie

/**
 *Created by farrukh_kh on 4/3/22 4:03 PM
 *kh.farrukh.arch_mvc.model
 **/
class SearchMovieResponse {
    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<Movie>? = null
}