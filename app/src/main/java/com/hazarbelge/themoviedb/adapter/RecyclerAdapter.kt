package com.hazarbelge.themoviedb.adapter

import android.content.Context
import android.content.Intent
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
import com.hazarbelge.themoviedb.ProfileActivity
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.dto.Movie
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class RecyclerAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewMovieHolder>() {

    private var movieList : List<Movie> = listOf()

    /*In this method we inflate the layout which we use in recyclerview. Then, it returns an object belongs ViewMovieHolder. This method is a member
    * of RecyclerView.Adapter. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewMovieHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_items,parent,false)
        return ViewMovieHolder(view)
    }

    /*getItemCount is a method of RecyclerView.Adapter too. It returns the size of list.*/
    override fun getItemCount(): Int {
        return movieList.size
    }

    /*Another member method is onBindViewHolder. This is where we bind the contents with layout's views. At the end of it we create an intent and
    * put inside a integer. It detect which item the user click on and start "ProfileActivity" with that item.*/
    override fun onBindViewHolder(holder: ViewMovieHolder, position: Int) {

        /*Formatting the date in pattern of "month dd, yyyy". To keep the minimum api low localdate or formatter
        * is used in if statement. They'll be used if only system's api is equal or more than 26. If not the date stay the pattern of "dd/MM/yyyy" */
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            val date = LocalDate.parse(movieList[position].release_date)
            val date_str = "${date.month.getDisplayName(TextStyle.FULL, Locale("tr")).toLowerCase(Locale.ROOT)} ${date.dayOfMonth}, ${date.year}"
            holder.movieDate.text = date_str
        } else {
            var date = movieList[position].release_date
            val date_year = date.removeRange(4,date.length)
            var date_month = date.removeRange(0,5)
            date_month = date_month.removeRange(2,date_month.length)
            val date_day = date.removeRange(0,8)
            date = "$date_day/$date_month/$date_year"
            holder.movieDate.text = date
        }

        holder.movieName.text = movieList[position].title
        holder.movieOverview.text = movieList[position].overview

        /*Glide is a superb function when we want to load an image from url into our ImageView. We take poster_path from response and put it next to
        * our constant path */
        Glide.with(context).load(context.getString(R.string.w220h330) + movieList[position].poster_path)
            .apply(RequestOptions().centerCrop())
            .into(holder.poster_path)

        /*We set a ClickListener into a clickable CardView. When the user click on it, it creates an intent and start the activity we want. In this
        * case it is "ProfileActivity" */
        holder.cardView.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("movieid", movieList[position].id)
            context.startActivity(intent)
        }
    }

    /*This method is where the response and this class member bind. After binding, we update the data with notifyDataSetChanged method.*/
    fun bind(movieList: List<Movie>){
        this.movieList = movieList
        notifyDataSetChanged()
    }

    /*After onCreateViewHolder we have an object of this class which has view and it binds the views inside our layout and the holder's variables. */
    class ViewMovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val movieName: TextView = itemView.findViewById(R.id.title)
        val poster_path: ImageView = itemView.findViewById(R.id.poster_path)
        val movieDate: TextView = itemView.findViewById(R.id.release_date)
        val movieOverview: TextView = itemView.findViewById(R.id.overview)
    }
}
