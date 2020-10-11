package com.hazarbelge.themoviedb.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hazarbelge.themoviedb.ProfileActivity
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.dto.Movie
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class RecyclerAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewMovieHolder>() {

    var movieList : List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewMovieHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_items,parent,false)
        return ViewMovieHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewMovieHolder, position: Int) {

        val date = LocalDate.parse(movieList[position].release_date)
        holder.movieDate.text = "${date.month.getDisplayName(TextStyle.FULL, Locale("tr")).toLowerCase()} ${date.dayOfMonth}, ${date.year}"

        holder.movieName.text = movieList[position].title
        holder.movieOverview.text = movieList[position].overview

        Glide.with(context).load("https://image.tmdb.org/t/p/w220_and_h330_face/" + movieList[position].poster_path)
            .apply(RequestOptions().centerCrop())
            .into(holder.poster_path)

        holder.cardView.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("movieid", movieList[position].id)
            context.startActivity(intent)
        }
    }

    fun bind(movieList: List<Movie>){
        this.movieList = movieList
        notifyDataSetChanged()
    }

    class ViewMovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val movieName: TextView = itemView.findViewById(R.id.title)
        val poster_path: ImageView = itemView.findViewById(R.id.poster_path)
        val movieDate: TextView = itemView.findViewById(R.id.release_date)
        val movieOverview: TextView = itemView.findViewById(R.id.overview)
    }
}
