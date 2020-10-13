package com.hazarbelge.themoviedb

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazarbelge.themoviedb.adapter.CastAdapter
import com.hazarbelge.themoviedb.adapter.ProfileAdapter
import com.hazarbelge.themoviedb.dto.Cast
import com.hazarbelge.themoviedb.dto.Crew
import com.hazarbelge.themoviedb.dto.Movie
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
        profile_logo.setImageResource(R.drawable.ic_logo)
        setSupportActionBar(toolbar)
        title = ""
        toolbar.minimumHeight = toolbar.height
        toolbar.setNavigationIcon(R.drawable.ic_menu)

        /*When this activity is created, a string which contains an id of the item comes with intent. Then, we send this string to our methods
        * to process about this item.*/
        val position = intent.getStringExtra("movieid")
        networkHandlerProfile(position!!)
        recyclerViewHandler(position)
        networkHandlerCrew(position)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    /*To use retrofit, to bind the response's contains and the views, we need an adapter. This time there is no RecyclerView and we can't use its
    * adapter. So we created a class called "ProfileAdapter" and it binds the views and sets their text, image or etc. In this method we create a
    * request and an object that belongs ProfileAdapter Class. The method inside this class will handle this. */
    private fun networkHandlerProfile(movieID: String) {

        val apiInterface = ApiInterface.create().getProfiles(movieID, getString(R.string.api_key), getString(
                R.string.language
            ))
        val adapter = ProfileAdapter()

        apiInterface.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                println("response: $response")
                adapter.responseHandler(response.body()!!, scrollview,this@ProfileActivity)
            }
            override fun onFailure(call: Call<Movie>?, t: Throwable?) {
                println("response failed: $t")
            }
        })

    }

    /*Generally same method with the method called recyclerViewHandler in "MainActivity". Two different things exist. Another RecyclerView and
    * the method that we'll use from the interface. */
    private fun recyclerViewHandler(movieID: String) {

        val recyclerView: RecyclerView = findViewById(R.id.recyclerStars)
        val recyclerAdapter = CastAdapter(this)
        /*The cast will be shown horizontally. Because of that our recyclerview's layoutManager orientation will be horizontal.  */
        recyclerStars.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        recyclerView.adapter = recyclerAdapter

        /*Like getMovie method that we used in MainActivity. But now, we send the movieID inside it to take endpoints of the specific movie.*/
        val apiInterface = ApiInterface.create().getCast(movieID, getString(R.string.api_key), getString(R.string.language))

        apiInterface.enqueue(object : Callback<Cast> {
            override fun onResponse(call: Call<Cast>, response: Response<Cast>) {
                println("response: $response")
                recyclerAdapter.bind(response.body()!!.cast)
            }
            override fun onFailure(call: Call<Cast>?, t: Throwable?) {
                println("response failed: $t")
            }
        })
    }

    private fun networkHandlerCrew(movieID: String) {
        val apiInterface = ApiInterface.create().getCrew(movieID, getString(R.string.api_key), getString(R.string.language))

        apiInterface.enqueue(object : Callback<Crew> {
            override fun onResponse(call: Call<Crew>, response: Response<Crew>) {
                println("response: $response")
                responseHandlerCrew(response.body()!!) //To bind this response with views, we send it to the method called responseHandlerCrew.
            }
            override fun onFailure(call: Call<Crew>?, t: Throwable?) {
                println("response failed: $t")
            }
        })
    }

    /*The binding process is happening in this method. In the layout there are 4 TextView and we'll set their text from here. */
    private fun responseHandlerCrew(crew: Crew){
        val screenplay: TextView = findViewById(R.id.screenplay)
        val director: TextView = findViewById(R.id.director)

        /*It searches the "job" endpoint to determine the director and the screen writer. When it finds, it sets the texts. */
        for(element in crew.crew) {
            if(element.job == "Director") {
                director.text = element.name
                directorText.text = element.job
            }
            else if(element.job == "Screenplay") {
                screenplay.text = element.name
                screenplayText.text = element.job
            }
        }
    }
}