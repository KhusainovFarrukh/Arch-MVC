package kh.farrukh.arch_mvc.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kh.farrukh.arch_mvc.R
import kh.farrukh.arch_mvc.databinding.ItemMovieMainBinding
import kh.farrukh.arch_mvc.data.Movie
import kh.farrukh.arch_mvc.data.remote.RetrofitClient.TMDB_IMAGE_URL
import kh.farrukh.arch_mvc.utils.load
import java.util.HashSet

/**
 *Created by farrukh_kh on 4/3/22 9:43 PM
 *kh.farrukh.arch_mvc.ui
 **/
class MainAdapter : ListAdapter<Movie, MainAdapter.MovieVH>(MAIN_MOVIE_ITEM_DIFF) {

    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieVH(
        ItemMovieMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(getItem(position))
    }

    fun clearSelectedMovies() {
        selectedMovies.clear()
    }

    inner class MovieVH(private val binding: ItemMovieMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.chbSelected.apply {
                setOnClickListener {
                    val adapterPosition = bindingAdapterPosition
                    if (selectedMovies.contains(getItem(adapterPosition))) {
                        isChecked = false
                        selectedMovies.remove(getItem(adapterPosition))
                    } else {
                        isChecked = true
                        selectedMovies.add(getItem(adapterPosition))
                    }
                }
            }
        }

        fun bind(movie: Movie) = with(binding) {
            tvTitle.text = movie.title
            tvReleaseYear.text = movie.releaseDate
            chbSelected.isChecked = selectedMovies.contains(movie)
            if (movie.posterPath.equals("")) {
                ivPoster.setImageDrawable(
                    ContextCompat.getDrawable(root.context, R.drawable.ic_local_movies_gray)
                )
            } else {
                ivPoster.load(TMDB_IMAGE_URL + movie.posterPath)
            }
        }
    }

    companion object {
        val MAIN_MOVIE_ITEM_DIFF = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.title == newItem.title &&
                        oldItem.getReleaseYearFromDate() == newItem.getReleaseYearFromDate() &&
                        oldItem.posterPath == newItem.posterPath
        }
    }
}