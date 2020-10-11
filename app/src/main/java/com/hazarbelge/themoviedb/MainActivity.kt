package com.hazarbelge.themoviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazarbelge.themoviedb.adapter.RecyclerAdapter
import com.hazarbelge.themoviedb.dto.Movies
import com.hazarbelge.themoviedb.network.ApiInterface
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = ""
        toolbar.setNavigationIcon(R.drawable.ic_menu)

        val string: String = getString(R.string.language)
        if(string == "tr") tView.text =  "Gösterimdeki Filmler"
        else if(string == "en") tView.text =  "Now Playing"

        recycleViewHandler()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    private fun recycleViewHandler() {

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        val recyclerAdapter = RecyclerAdapter(this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        val apiInterface = ApiInterface.create().getMovies(getString(R.string.api_key),getString(R.string.language))

        apiInterface.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                println("response var: " + response)
                recyclerAdapter.bind(response.body()!!.results)
            }
            override fun onFailure(call: Call<Movies>?, t: Throwable?) {
                println("response basarisiz: " + t)
            }
        })
    }
}