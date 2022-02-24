package com.hazarbelge.themoviedb.ui.main.views.now_playing.adapter

import android.os.Build.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.network.model.Movie
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class NowPlayingAdapter(
    private var callback: ItemClickListener<Movie>,
    private val movieList: List<Movie>,
) : RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingAdapter.NowPlayingViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.movie_items, null)
        return NowPlayingViewHolder(inflate)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(
        viewHolder: NowPlayingAdapter.NowPlayingViewHolder,
        position: Int
    ) {
        val movie: Movie = movieList[position]

        /**
         * Formatting the date in pattern of "month dd, yyyy". To keep the minimum api low localdate or formatter
         * is used in if statement. They'll be used if only system's api is equal or more than 26. If not the date stay
         * the pattern of "dd/MM/yyyy"
         */
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            val date = LocalDate.parse(movie.release_date)
            val dateStr = "${
                date.month.getDisplayName(TextStyle.FULL, Locale("tr")).lowercase(Locale.ROOT)
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

        viewHolder.posterPath.apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            load(context.getString(R.string.w220h330) + movie.poster_path)
        }

        viewHolder.cardView.setOnClickListener {
            callback.onItemClicked(
                viewHolder.cardView,
                movie,
            )
        }
    }

    inner class NowPlayingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val movieName: TextView = itemView.findViewById(R.id.title)
        val posterPath: ImageView = itemView.findViewById(R.id.poster_path)
        val movieReleaseDate: TextView = itemView.findViewById(R.id.release_date)
        val movieOverview: TextView = itemView.findViewById(R.id.overview)
    }
}
