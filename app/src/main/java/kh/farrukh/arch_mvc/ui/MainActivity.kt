package kh.farrukh.arch_mvc.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kh.farrukh.arch_mvc.R
import kh.farrukh.arch_mvc.databinding.ActivityMainBinding
import kh.farrukh.arch_mvc.data.local.LocalDataSource
import kh.farrukh.arch_mvc.data.Movie
import kh.farrukh.arch_mvc.data.local.LocalDatabase
import kh.farrukh.arch_mvc.utils.startActivityForResult
import kh.farrukh.arch_mvc.utils.toast
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val mainAdapter by lazy { MainAdapter() }
    private val dataSource by lazy { initLocalDataSource() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        getMyMoviesList()
    }

    private fun setupViews() = with(binding) {
        rvMovies.adapter = mainAdapter
        fabAddMovie.setOnClickListener { goToAddMovieActivity() }
        supportActionBar?.title = "Movies to Watch"
    }

    private fun getMyMoviesList() {
        lifecycleScope.launchWhenStarted {
            // TODO: error handle
            dataSource.allMovies.collect { movies -> displayMovies(movies) }
        }
    }

    private fun displayMovies(movieList: List<Movie>) = with(binding) {
        rvMovies.isVisible = movieList.isNotEmpty()
        layoutEmpty.isVisible = movieList.isEmpty()

        if (movieList.isNotEmpty()) mainAdapter.submitList(movieList)
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
        if (result?.resultCode == Activity.RESULT_OK) {
            toast("Movie successfully added.")
        } else {
            displayError("Movie could not be added.")
        }
    }

    private fun displayError(e: String) {
        toast(e)
    }

    private fun initLocalDataSource() = LocalDataSource(
        LocalDatabase.getInstance(application).movieDao(), Dispatchers.IO
    )
}