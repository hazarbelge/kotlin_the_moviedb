package com.hazarbelge.themoviedb.adapter

import android.content.Context
import android.icu.text.StringSearch
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.dto.Movie
import kotlinx.android.synthetic.main.activity_profile.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProfileAdapter(){

    fun responseHandler(movie: Movie, view: View, context: Context){

        val movieName: TextView = view.findViewById(R.id.profile_title)
        val poster_path: ImageView = view.findViewById(R.id.poster)
        val backdrop_path: ImageView = view.findViewById(R.id.bg_poster)
        val movieStatus: ImageView = view.findViewById(R.id.profile_status)
        val movieDate: TextView = view.findViewById(R.id.profile_release_date)
        val movieOverview: TextView = view.findViewById(R.id.profile_overview)
        val movieGenres: TextView = view.findViewById(R.id.profile_genres)
        val movieHeadline: TextView = view.findViewById(R.id.profile_headline)
        val movieMembers: TextView = view.findViewById(R.id.profile_members)
        val movieTagline: TextView = view.findViewById(R.id.profile_tagline)
        val movieRuntime: TextView = view.findViewById(R.id.profile_runtime)
        val movieActors: TextView = view.findViewById(R.id.profile_stars)
        val movieProgress: ProgressBar = view.findViewById(R.id.progressBar)

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = LocalDate.parse(movie.release_date)
        movieDate.text = formatter.format(date) + " (${movie.production_countries[0]["iso_3166_1"]})"

        var genres:String? = ""
        for(element in movie.genres) genres += "${element["name"]}, "
        movieGenres.text = genres!!.substring(0, genres.length-2);

        if(movie.runtime.toInt() >= 60) movieRuntime.text = "${movie.runtime.toInt()/60}h ${movie.runtime.toInt()%60}m"
        else movieRuntime.text = movie.runtime

        val text: String = "<font color=#ffffff>${movie.title}</font> <font color=#cce0e3>(${date.year})</font>"
        movieName.text = Html.fromHtml(text)

        movieTagline.text = movie.tagline
        movieOverview.text = movie.overview

        val string: String = context.getString(R.string.language)
        if(string == "tr") {
            movieHeadline.text =  "Özet"
            movieMembers.text = "Üye Puanları"
            movieActors.text = "Başrol Oyuncuları"
        }
        else if(string == "en"){
            movieHeadline.text =  "Overview"
            movieMembers.text = "Members Vote"
            movieActors.text = "Cast"
        }

        movieProgress.progress = (movie.vote_average*10).toInt()
        view.progressText.text = (movie.vote_average*10).toInt().toString() + "%"

        if(movie.status.equals("Released")) movieStatus.setImageResource(R.drawable.ic_released)
        Glide.with(context).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + movie.poster_path)
            .apply(RequestOptions().centerCrop())
            .into(poster_path)
        Glide.with(context).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2/" + movie.backdrop_path)
            .apply(RequestOptions().centerCrop())
            .into(backdrop_path)
    }

}