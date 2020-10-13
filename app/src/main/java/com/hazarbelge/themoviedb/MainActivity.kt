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
import kotlinx.android.synthetic.main.activity_main.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*The App's all activities have no action bar, so Toolbar was added from layout and now it must be set for ActionBar. After that, title
        * should be empty and toolbar's minimum height must be equal toolbar' height. The reason behind this code is buttons on the toolbar
        * should be in center vertically*/
        logo.setImageResource(R.drawable.ic_logo)
        setSupportActionBar(toolbar)
        title = ""
        toolbar.minimumHeight = toolbar.height
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        tView.text =  getString(R.string.now_playing)

        recycleViewHandler()
    }

    /*In the layout "actionbar_menu" there are 2 items and they must be on the toolbar. menuInflater is doing this task.*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    private fun recycleViewHandler() {

        /*Activity's RecyclerView(its id is recyclerview) needs an LinearLayoutManager and adapter. The Class "RecyclerAdapter"
        * is the adapter for it. */
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        val recyclerAdapter = RecyclerAdapter(this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        /* In the interface there is a method called "create" it has retrofit variable and with that it builds retrofit with base_url. Then,
        * it returns an interface and with this interface we can use parameters like Get, Path or Post. When we send request that url there are
        * 2 options. Success and failure. If it is successful then we can bind our class and the contains of response which is response.body().
        * On the other hand if the request isn't work method throw an exception*/
        val apiInterface = ApiInterface.create().getMovies(getString(R.string.api_key),getString(R.string.language))
        apiInterface.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                println("response: $response")
                recyclerAdapter.bind(response.body()!!.results)
            }
            override fun onFailure(call: Call<Movies>?, t: Throwable?) {
                println("response failed: $t")
            }
        })
    }
}