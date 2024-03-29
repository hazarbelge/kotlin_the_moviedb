package com.hazarbelge.themoviedb.ui.main.views.latest.adapter

import android.os.Build.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.util.ItemClickListener
import com.hazarbelge.themoviedb.network.LANGUAGE
import com.hazarbelge.themoviedb.model.Movie
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class LatestAdapter(
    private var callback: ItemClickListener<Movie>,
    private val movie: Movie,
) : RecyclerView.Adapter<LatestAdapter.LatestViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LatestViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.movie_items, null)
        return LatestViewHolder(inflate)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(
        viewHolder: LatestViewHolder,
        position: Int
    ) {
        val movie: Movie = movie

        if(movie.release_date.isNotEmpty()) {
            if (VERSION.SDK_INT >= VERSION_CODES.O) {
                val date = LocalDate.parse(movie.release_date)
                val dateStr = "${
                    date.month.getDisplayName(TextStyle.FULL, Locale(LANGUAGE)).lowercase(Locale.ROOT)
                } ${date.dayOfMonth}, ${date.year}"

                viewHolder.movieReleaseDate.apply {
                    text = dateStr
                }
            } else {
                var date = movie.release_date
                val dateYear = date.removeRange(4, date.length)
                var dateMonth = date.removeRange(0, 5)
                dateMonth = dateMonth.removeRange(2, dateMonth.length)
                val dateDay = date.removeRange(0, 8)
                date = "$dateDay/$dateMonth/$dateYear"

                viewHolder.movieReleaseDate.apply {
                    text = date
                }
            }
        }

        viewHolder.movieName.apply {
            text = movie.title
        }

        viewHolder.movieOverview.apply {
            text = movie.overview
        }

        if(movie.poster_path != null) {
            Glide.with(viewHolder.posterPath.context).load(viewHolder.posterPath.context.getString(R.string.w220h330) + movie.poster_path)
                .apply(RequestOptions().centerCrop())
                .into(viewHolder.posterPath)
        }

        viewHolder.cardView.setOnClickListener {
            callback.onItemClicked(
                viewHolder.cardView,
                movie,
            )
        }
    }

    inner class LatestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val movieName: TextView = itemView.findViewById(R.id.title)
        val posterPath: ImageView = itemView.findViewById(R.id.poster_path)
        val movieReleaseDate: TextView = itemView.findViewById(R.id.release_date)
        val movieOverview: TextView = itemView.findViewById(R.id.overview)
    }
}
