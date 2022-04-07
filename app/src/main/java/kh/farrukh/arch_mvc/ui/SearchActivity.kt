package kh.farrukh.arch_mvc.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import dagger.hilt.android.AndroidEntryPoint
import kh.farrukh.arch_mvc.R
import kh.farrukh.arch_mvc.data.Movie
import kh.farrukh.arch_mvc.data.remote.RemoteDataSource
import kh.farrukh.arch_mvc.data.remote.SearchMovieResponse
import kh.farrukh.arch_mvc.databinding.ActivitySearchBinding
import kh.farrukh.arch_mvc.utils.handle
import org.reactivestreams.Subscription
import javax.inject.Inject

/**
 *Created by farrukh_kh on 4/6/22 5:49 PM
 *kh.farrukh.arch_mvc.ui
 **/
@AndroidEntryPoint
class SearchActivity : AppCompatActivity(R.layout.activity_search) {

    private val binding by viewBinding(ActivitySearchBinding::bind)
    private val searchAdapter by lazy { SearchAdapter(::onMovieClick) }
    private val dataSource by lazy { initRemoteDataSource() }
    private var resultsSubscription: Subscription? = null

    @Inject
    lateinit var dataSource: RemoteDataSource

    private val query by lazy { intent?.getStringExtra(SEARCH_QUERY) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    override fun onStart() {
        super.onStart()
        getSearchResults(query)
    }

    override fun onStop() {
        super.onStop()
        resultsSubscription?.cancel()
        resultsSubscription = null
    }

    private fun setupViews() = with(binding) {
        rvSearchResults.adapter = searchAdapter
    }

    private fun getSearchResults(query: String) {
        dataSource.searchResults(query)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { subscription -> resultsSubscription = subscription }
            .subscribe(
                { result -> result.handle(::displayResults, ::displayError) },
                ::displayError
            )
    }

    private fun displayResults(response: SearchMovieResponse) = with(binding) {
        pbLoading.isVisible = false
        tvNoMovies.isVisible = response.results.isNullOrEmpty()

        searchAdapter.submitList(response.results)
        if (response.results.isNullOrEmpty()) tvNoMovies.text = "No movies found"
    }

    private fun displayError(throwable: Throwable?) = with(binding) {
        pbLoading.isVisible = false
        rvSearchResults.isVisible = false
        tvNoMovies.isVisible = true
        tvNoMovies.text = throwable?.message ?: "Something went wrong"
    }

    private fun onMovieClick(movie: Movie) {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_TITLE, movie.title)
            putExtra(EXTRA_RELEASE_DATE, movie.getReleaseYearFromDate())
            putExtra(EXTRA_POSTER_PATH, movie.posterPath)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun initRemoteDataSource() = RemoteDataSource(RetrofitClient.moviesApi)

    companion object {
        const val SEARCH_QUERY = "searchQuery"
        const val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
        const val EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY"
        const val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
    }
}