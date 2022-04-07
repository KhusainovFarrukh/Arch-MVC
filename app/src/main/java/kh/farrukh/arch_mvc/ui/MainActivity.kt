package kh.farrukh.arch_mvc.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kh.farrukh.arch_mvc.R
import kh.farrukh.arch_mvc.data.Movie
import kh.farrukh.arch_mvc.data.local.LocalDataSource
import kh.farrukh.arch_mvc.data.local.LocalDatabase
import kh.farrukh.arch_mvc.databinding.ActivityMainBinding
import kh.farrukh.arch_mvc.utils.startActivityForResult
import kh.farrukh.arch_mvc.utils.toast
import org.reactivestreams.Subscription

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val mainAdapter by lazy { MainAdapter() }

    @Inject
    lateinit var dataSource: LocalDataSource

    private var allMoviesSubscription: Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        getMyMoviesList()
    }

    override fun onStop() {
        super.onStop()
        allMoviesSubscription?.cancel()
        allMoviesSubscription = null
    }

    private fun setupViews() = with(binding) {
        rvMovies.adapter = mainAdapter
        fabAddMovie.setOnClickListener { goToAddMovieActivity() }
        supportActionBar?.title = "Movies to Watch"
    }

    private fun getMyMoviesList() {
        dataSource.allMovies
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { subscription -> allMoviesSubscription = subscription }
            .subscribe(::displayMovies, ::displayError)
    }

    private fun displayMovies(movieList: List<Movie>) = with(binding) {
        layoutEmpty.isVisible = movieList.isEmpty()
        mainAdapter.submitList(movieList)
    }

    private fun goToAddMovieActivity() {
        addMovieLauncher.launch(Intent(this, AddMovieActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenuItem) {
            for (movie in mainAdapter.selectedMovies) {
                dataSource.delete(movie)
            }
            mainAdapter.clearSelectedMovies()

            if (mainAdapter.selectedMovies.size == 1) {
                toast("Movie deleted")
            } else if (mainAdapter.selectedMovies.size > 1) {
                toast("Movies deleted")
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private val addMovieLauncher = startActivityForResult { result ->
        when (result?.resultCode) {
            Activity.RESULT_OK -> toast("Movie successfully added")
            Activity.RESULT_CANCELED -> toast("No movie provided to add")
            else -> toast("Movie could not been added")
        }
    }

    private fun displayError(error: Throwable) {
        toast(error.message ?: "Unknown error")
    }
}