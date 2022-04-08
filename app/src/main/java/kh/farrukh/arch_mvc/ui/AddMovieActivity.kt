package kh.farrukh.arch_mvc.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kh.farrukh.arch_mvc.R
import kh.farrukh.arch_mvc.data.Movie
import kh.farrukh.arch_mvc.data.local.LocalDataSource
import kh.farrukh.arch_mvc.data.remote.RetrofitClient.TMDB_IMAGE_URL
import kh.farrukh.arch_mvc.databinding.ActivityAddMovieBinding
import kh.farrukh.arch_mvc.utils.getString
import kh.farrukh.arch_mvc.utils.load
import kh.farrukh.arch_mvc.utils.startActivityForResult
import kh.farrukh.arch_mvc.utils.toast
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by farrukh_kh on 4/3/22 10:26 PM
 *kh.farrukh.arch_mvc.ui
 **/
@AndroidEntryPoint
class AddMovieActivity : AppCompatActivity(R.layout.activity_add_movie) {

    private val binding by viewBinding(ActivityAddMovieBinding::bind)

    @Inject
    lateinit var dataSource: LocalDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() = with(binding) {
        btnSearch.setOnClickListener { goToSearchMovieActivity() }
        btnAddMovie.setOnClickListener { addMovie() }
    }

    private fun goToSearchMovieActivity() {
        val intent = Intent(this, SearchActivity::class.java).apply {
            putExtra(SearchActivity.SEARCH_QUERY, binding.tetTitle.text.toString())
        }
        searchMovieLauncher.launch(intent)
    }

    private fun addMovie() = with(binding) {

        if (TextUtils.isEmpty(tetTitle.text)) {
            toast("Movie title cannot be empty")
        } else {
            lifecycleScope.launch {
                val title = tetTitle.text.toString()
                val releaseDate = tetReleaseYear.text.toString()
                val posterPath = if (ivPoster.tag != null) ivPoster.tag.toString() else ""

                val movie = Movie(title = title, releaseDate = releaseDate, posterPath = posterPath)
                dataSource.insert(movie)

                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private val searchMovieLauncher = startActivityForResult { result ->
        when (result?.resultCode) {
            Activity.RESULT_OK -> binding.apply {
                tetTitle.setText(result.getString(SearchActivity.EXTRA_TITLE) ?: "")
                tetReleaseYear.setText(
                    result.getString(SearchActivity.EXTRA_RELEASE_DATE) ?: ""
                )
                ivPoster.tag = result.getString(SearchActivity.EXTRA_POSTER_PATH)
                ivPoster.load(TMDB_IMAGE_URL + result.getString(SearchActivity.EXTRA_POSTER_PATH))
            }
            Activity.RESULT_CANCELED -> toast("No movie selected")
            else -> toast("Something went wrong")
        }
    }
}