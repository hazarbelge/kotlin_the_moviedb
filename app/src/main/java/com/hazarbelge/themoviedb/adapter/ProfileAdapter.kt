package com.hazarbelge.themoviedb.adapter

import android.content.Context
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

class ProfileAdapter(){

    /**
     * This method takes 3 parameters: "movie" parameter is response's contains, "view" is our ScrollView and there is context which is our activity.
     * We take the contains of response in here and the responseHandler method binds the views and sets their text, image or edit the ProgressBar.
     */
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

        /**
         * Formatting the release date in pattern of "dd/MM/yyyy" and next to it where it was produced. To keep the minimum api low localdate or formatter
         * is not used. (they require min api 26)
         */
        var date = movie.release_date
        val date_year = date.removeRange(4,date.length)
        var date_month = date.removeRange(0,5)
        date_month = date_month.removeRange(2,date_month.length)
        val date_day = date.removeRange(0,8)
        date = "$date_day/$date_month/$date_year" + " (${movie.production_countries[0]["iso_3166_1"]})"
        movieDate.text = date

        /**
         * The title text has 2 different color. So, we use Html.fromHtml to determine the colors.
         */
        val text = "<font color=#ffffff>${movie.title}</font> <font color=#cce0e3>(${movie.release_date.removeRange(4,movie.release_date.length)})</font>"
        movieName.text = Html.fromHtml(text)

        var genres:String? = ""
        for(element in movie.genres) genres += "${element["name"]}, "
        movieGenres.text = genres!!.substring(0, genres.length-2) //Deleting the last 2 characters of string is necessary, because they are "," and " "

        /**
         * Formatting the runtime in pattern of "(int)h(int)m"
         */
        val runtime_str = "${movie.runtime.toInt()/60}h ${movie.runtime.toInt()%60}m"
        if(movie.runtime.toInt() >= 60) movieRuntime.text = runtime_str
        else movieRuntime.text = movie.runtime

        movieTagline.text = movie.tagline
        movieOverview.text = movie.overview

        /**
         * If there are no tagline or overview, they must be disappear. When they not, there is a huge amounts of space. That's why when they are not
         * exists, we set their visibility to View.GONE
         */
        if(movie.tagline == "") movieTagline.visibility = View.GONE
        if(movie.overview == "") movieHeadline.visibility = View.GONE
        else movieHeadline.text = context.getString(R.string.overview) //We want the headline "Özet" exists if there is a overview.

        movieMembers.text = context.getString(R.string.members_vote)
        movieActors.text = context.getString(R.string.cast)

        movieProgress.progress = (movie.vote_average*10).toInt()
        val progress_str:String = movieProgress.progress.toString() + "%" //In ProgressBar we have a TextView that shows us the progress and we set that text with % sign
        view.progressText.text = progress_str

        if(movie.status == "Released") movieStatus.setImageResource(R.drawable.ic_released) //If the movie's status is "Released", it shows the drawable "R" next to the released date.

        Glide.with(context).load(context.getString(R.string.w600h900) + movie.poster_path)
            .apply(RequestOptions().centerCrop())
            .into(poster_path)
        Glide.with(context).load(context.getString(R.string.w533h300) + movie.backdrop_path)
            .apply(RequestOptions().centerCrop())
            .into(backdrop_path)
    }

}