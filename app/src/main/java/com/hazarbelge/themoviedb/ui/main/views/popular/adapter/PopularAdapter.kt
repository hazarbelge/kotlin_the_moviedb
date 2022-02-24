package com.hazarbelge.themoviedb.ui.main.views.popular.adapter

import android.os.Build.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.network.model.Movie
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class PopularAdapter(
    private var callback: ItemClickListener<Movie>,
    private val movieList: List<Movie>,
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.movie_items, null)
        return PopularViewHolder(inflate)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(
        viewHolder: PopularViewHolder,
        position: Int
    ) {
        val movie: Movie = movieList[position]

        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            val date = LocalDate.parse(movie.release_date)
            val dateStr = "${
                date.month.getDisplayName(TextStyle.FULL, Locale("tr"))
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

    inner class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val movieName: TextView = itemView.findViewById(R.id.title)
        val posterPath: ImageView = itemView.findViewById(R.id.poster_path)
        val movieReleaseDate: TextView = itemView.findViewById(R.id.release_date)
        val movieOverview: TextView = itemView.findViewById(R.id.overview)
    }
}
