package com.hazarbelge.themoviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hazarbelge.themoviedb.adapter.ProfileAdapter
import com.hazarbelge.themoviedb.dto.Movie
import com.hazarbelge.themoviedb.dto.Movies
import com.hazarbelge.themoviedb.network.ApiInterface
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu)

        //bnView.menu.getItem(0).isChecked = true
        bnavItemHandler()

        val position = intent.getStringExtra("movieid")
        recyclerHandler(position!!)
    }

    private fun recyclerHandler(movieID: String) {

        val apiInterface = ApiInterface.create().getProfiles(movieID,getString(R.string.api_key),getString(R.string.language))

        apiInterface.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                println("response var: " + response)
                ProfileAdapter(this@ProfileActivity,response.body()!!)
                val recyclerView: RecyclerView = findViewById(R.id.recyclerProfile)
                val recyclerAdapter = ProfileAdapter(this@ProfileActivity,response.body()!!)
                recyclerProfile.layoutManager = LinearLayoutManager(this@ProfileActivity)
                recyclerView.adapter = recyclerAdapter
            }

            override fun onFailure(call: Call<Movie>?, t: Throwable?) {
                println("response basarisiz: " + t)
            }
        })
    }

    private fun bnavItemHandler() {
        bnView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuBNV ->  {}
                R.id.favBNV ->  {}
                R.id.bookmarkBNV ->{}
                R.id.starBNV ->{}
            }
            return@OnNavigationItemSelectedListener false
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }
}