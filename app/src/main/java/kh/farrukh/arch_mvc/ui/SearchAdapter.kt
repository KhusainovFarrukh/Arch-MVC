package kh.farrukh.arch_mvc.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kh.farrukh.arch_mvc.databinding.ItemMovieSearchBinding
import kh.farrukh.arch_mvc.data.Movie
import kh.farrukh.arch_mvc.data.remote.RetrofitClient.TMDB_IMAGE_URL
import kh.farrukh.arch_mvc.utils.SingleBlock
import kh.farrukh.arch_mvc.utils.load

/**
 *Created by farrukh_kh on 4/6/22 5:50 PM
 *kh.farrukh.arch_mvc.ui
 **/
class SearchAdapter(
    private val onMovieClick: SingleBlock<Movie>
) : ListAdapter<Movie, SearchAdapter.VH>(SEARCH_MOVIE_ITEM_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemMovieSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VH(private val binding: ItemMovieSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onMovieClick(getItem(bindingAdapterPosition)) }
        }

        fun bind(movie: Movie) = with(binding) {
            tvTitle.text = movie.title
            tvReleaseDate.text = movie.getReleaseYearFromDate()
            tvOverview.text = movie.overview

            ivPoster.load(TMDB_IMAGE_URL + movie.posterPath)
        }
    }

    companion object {
        val SEARCH_MOVIE_ITEM_DIFF = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.title == newItem.title &&
                        oldItem.getReleaseYearFromDate() == newItem.getReleaseYearFromDate() &&
                        oldItem.posterPath == newItem.posterPath &&
                        oldItem.overview == newItem.overview
        }
    }
}