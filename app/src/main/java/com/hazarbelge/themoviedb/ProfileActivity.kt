package com.hazarbelge.themoviedb

import android.os.Bundle
import android.view.LayoutInflater
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
import com.hazarbelge.themoviedb.adapter.ProfileAdapter
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

        val position = intent.getStringExtra("movieid")
        networkHandlerProfile(position!!)
        networkHandlerCast(position)
        networkHandlerCrew(position)

        bnavItemHandler()
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

        val adapter = ProfileAdapter()

        apiInterface.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                println("response var: " + response)
                adapter.responseHandler(response.body()!!, scrollview,this@ProfileActivity)
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
                responseHandlerCrew(response.body()!!)
            }

            override fun onFailure(call: Call<Crew>?, t: Throwable?) {
                println("response basarisiz: " + t)
            }
        })
    }

    private fun responseHandlerCrew(crew: Crew){
        val screenplay: TextView = findViewById(R.id.screenplay)
        val director: TextView = findViewById(R.id.director)

        for(element in crew.crew) {
            if(element.job.equals("Director")) {
                director.text = element.name
                directorText.text = element.job
            }
            else if(element.job.equals("Screenplay")) {
                screenplay.text = element.name
                screenplayText.text = element.job
            }
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