package com.hazarbelge.themoviedb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.dto.Movie
import kotlin.time.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ProfileAdapter(val context: Context, var movie: Movie) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_items,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    @ExperimentalTime
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = LocalDate.parse(movie.release_date)
        holder.tvMovieDate.text = formatter.format(date) + " (${movie.production_countries[0]["iso_3166_1"]})"

        var genres:String? = ""
        for(element in movie.genres) genres += "${element["name"]}, "
        holder.tvMovieGenres.text = genres!!.substring(0, genres.length-2);

        if(movie.runtime.toInt() >= 60) holder.tvMovieRuntime.text = "${movie.runtime.toInt()/60}h ${movie.runtime.toInt()%60}m"
        else holder.tvMovieRuntime.text = movie.runtime

        holder.tvMovieTagline.text = movie.tagline
        holder.tvMovieName.text = "${movie.title} (${date.year})"
        holder.tvMovieOverview.text = movie.overview
        holder.tvMovieHeadline.text = "Özet"
        holder.tvMovieMembers.text = "Üye Puanları"
        holder.tvMovieStatus.setImageResource(R.drawable.ic_released)
        Glide.with(context).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + movie.poster_path)
            .apply(RequestOptions().centerCrop())
            .into(holder.poster_path)
        Glide.with(context).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2/" + movie.backdrop_path)
            .apply(RequestOptions().centerCrop())
            .into(holder.backdrop_path)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvMovieName: TextView = itemView.findViewById(R.id.profile_title)
        val poster_path: ImageView = itemView.findViewById(R.id.poster)
        val backdrop_path: ImageView = itemView.findViewById(R.id.bg_poster)
        val tvMovieStatus: ImageView = itemView.findViewById(R.id.profile_status)
        val tvMovieDate: TextView = itemView.findViewById(R.id.profile_release_date)
        val tvMovieOverview: TextView = itemView.findViewById(R.id.profile_overview)
        val tvMovieGenres: TextView = itemView.findViewById(R.id.profile_genres)
        val tvMovieHeadline: TextView = itemView.findViewById(R.id.profile_headline)
        val tvMovieMembers: TextView = itemView.findViewById(R.id.profile_members)
        val tvMovieTagline: TextView = itemView.findViewById(R.id.profile_tagline)
        val tvMovieRuntime: TextView = itemView.findViewById(R.id.profile_runtime)
    }
}
