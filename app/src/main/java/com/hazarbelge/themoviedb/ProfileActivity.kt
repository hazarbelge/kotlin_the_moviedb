package com.hazarbelge.themoviedb

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hazarbelge.themoviedb.adapter.CastAdapter
import com.hazarbelge.themoviedb.dto.Cast
import com.hazarbelge.themoviedb.dto.Crew
import com.hazarbelge.themoviedb.dto.Movie
import com.hazarbelge.themoviedb.network.ApiInterface
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu)

        bnavItemHandler()

        val position = intent.getStringExtra("movieid")
        networkHandlerProfile(position!!)
        networkHandlerCast(position)
        networkHandlerCrew(position)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    private fun networkHandlerProfile(movieID: String) {

        val apiInterface = ApiInterface.create().getProfiles(
            movieID, getString(R.string.api_key), getString(
                R.string.language
            )
        )

        apiInterface.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                println("response var: " + response)
                responseHandler(response.body()!!)
            }

            override fun onFailure(call: Call<Movie>?, t: Throwable?) {
                println("response basarisiz: " + t)
            }
        })

    }

    private fun networkHandlerCast(movieID: String) {

        val recyclerView: RecyclerView = findViewById(R.id.recyclerStars)
        val recyclerAdapter = CastAdapter(this)
        recyclerStars.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        recyclerView.adapter = recyclerAdapter

        val apiInterface = ApiInterface.create().getCast(movieID, getString(R.string.api_key), getString(R.string.language))

        apiInterface.enqueue(object : Callback<Cast> {
            override fun onResponse(call: Call<Cast>, response: Response<Cast>) {
                println("response var: " + response)
                recyclerAdapter.setActorListItems(response.body()!!.cast)
            }

            override fun onFailure(call: Call<Cast>?, t: Throwable?) {
                println("response basarisiz: " + t)
            }
        })
    }

    private fun networkHandlerCrew(movieID: String) {
        val apiInterface = ApiInterface.create().getCrew(movieID, getString(R.string.api_key), getString(R.string.language))

        apiInterface.enqueue(object : Callback<Crew> {
            override fun onResponse(call: Call<Crew>, response: Response<Crew>) {
                println("response var: " + response)
                responseHandler(response.body()!!)
            }

            override fun onFailure(call: Call<Crew>?, t: Throwable?) {
                println("response basarisiz: " + t)
            }
        })
    }

    private fun responseHandler(movie: Movie){

        val movieName: TextView = findViewById(R.id.profile_title)
        val poster_path: ImageView = findViewById(R.id.poster)
        val backdrop_path: ImageView = findViewById(R.id.bg_poster)
        val movieStatus: ImageView = findViewById(R.id.profile_status)
        val movieDate: TextView = findViewById(R.id.profile_release_date)
        val movieOverview: TextView = findViewById(R.id.profile_overview)
        val movieGenres: TextView = findViewById(R.id.profile_genres)
        val movieHeadline: TextView = findViewById(R.id.profile_headline)
        val movieMembers: TextView = findViewById(R.id.profile_members)
        val movieTagline: TextView = findViewById(R.id.profile_tagline)
        val movieRuntime: TextView = findViewById(R.id.profile_runtime)
        val movieProgress: ProgressBar = findViewById(R.id.progressBar)

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = LocalDate.parse(movie.release_date)
        movieDate.text = formatter.format(date) + " (${movie.production_countries[0]["iso_3166_1"]})"

        var genres:String? = ""
        for(element in movie.genres) genres += "${element["name"]}, "
        movieGenres.text = genres!!.substring(0, genres.length-2);

        if(movie.runtime.toInt() >= 60) movieRuntime.text = "${movie.runtime.toInt()/60}h ${movie.runtime.toInt()%60}m"
        else movieRuntime.text = movie.runtime

        movieTagline.text = movie.tagline
        movieName.text = "${movie.title} (${date.year})"
        movieOverview.text = movie.overview
        movieHeadline.text = "Özet"
        movieMembers.text = "Üye Puanları"

        movieProgress.progress = (movie.vote_average*10).toInt()
        progressText.text = (movie.vote_average*10).toInt().toString() + "%"

        if(movie.status.equals("Released")) movieStatus.setImageResource(R.drawable.ic_released)
        Glide.with(this).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + movie.poster_path)
            .apply(RequestOptions().centerCrop())
            .into(poster_path)
        Glide.with(this).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2/" + movie.backdrop_path)
            .apply(RequestOptions().centerCrop())
            .into(backdrop_path)
    }

    private fun responseHandler(crew: Crew){
        val screenplay: TextView = findViewById(R.id.screenplay)
        val director: TextView = findViewById(R.id.director)

        for(element in crew.crew) {
            if(element.job.equals("Director")) director.text = element.name + "\n" + element.job
            else if(element.job.equals("Screenplay")) screenplay.text = element.name + "\n" + element.job
        }
    }

    private fun bnavItemHandler() {
        bnView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuBNV -> {
                }
                R.id.favBNV -> {
                }
                R.id.bookmarkBNV -> {
                }
                R.id.starBNV -> {
                }
            }
            return@OnNavigationItemSelectedListener false
        })
    }
}