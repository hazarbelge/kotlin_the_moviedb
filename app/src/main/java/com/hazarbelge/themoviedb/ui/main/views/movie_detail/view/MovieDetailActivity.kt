package com.hazarbelge.themoviedb.ui.main.views.movie_detail.view

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.base.BaseActivity
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.databinding.ActivityMovieDetailBinding
import com.hazarbelge.themoviedb.network.model.Actor
import com.hazarbelge.themoviedb.ui.main.views.movie_detail.adapter.CastAdapter
import com.hazarbelge.themoviedb.ui.main.views.movie_detail.viewmodel.MovieDetailViewModel
import com.hazarbelge.themoviedb.network.model.Crew
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Result

class MovieDetailActivity : BaseActivity<MovieDetailViewModel, ActivityMovieDetailBinding>(),
    ItemClickListener<Actor> {

    override val binding: ActivityMovieDetailBinding by lazy {
        ActivityMovieDetailBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.topNavBar.toolbar)

        binding.topNavBar.apply {
            title = ""
            returnImageView.setOnClickListener {
                onBackPressed()
            }
        }

        val position = intent.getStringExtra("movieid")
        getMovie(position!!)
        getCast(position)
        getCrew(position)
    }

    private fun getMovie(movieID: String) {
        viewModel.getMovieById(movieID).observe(this) {
            if (it is Result.Success) {
                movieDetailResponseHandler(it.data)
            }
        }
    }

    private fun getCast(movieID: String) {
        viewModel.getCast(movieID).observe(this) {
            if (it is Result.Success) {
                val recyclerAdapter = CastAdapter(this, it.data.cast)

                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(
                        this@MovieDetailActivity,
                        RecyclerView.HORIZONTAL,
                        false
                    )
                    adapter = recyclerAdapter
                }
            }
        }
    }

    private fun getCrew(movieID: String) {
        viewModel.getCrew(movieID).observe(this) {
            if (it is Result.Success) {
                responseHandlerCrew(it.data)
            }
        }
    }

    private fun responseHandlerCrew(crew: Crew) {
        for (element in crew.crew) {
            if (element.job == "Director") {
                binding.director.apply {
                    text = element.name
                }
                binding.directorText.apply {
                    text = element.job
                }
            } else if (element.job == "Screenplay") {
                binding.screenplay.apply {
                    text = element.name
                }
                binding.screenplayText.apply {
                    text = element.job
                }
            }
        }
    }

    private fun movieDetailResponseHandler(movie: Movie) {

        /**
         * Formatting the release date in pattern of "dd/MM/yyyy" and next to it where it was produced. To keep the minimum api low localdate or formatter
         * is not used. (they require min api 26)
         */
        if(movie.release_date.isNotEmpty()) {
            var date = movie.release_date
            val dateYear = date.removeRange(4, date.length)
            var dateMonth = date.removeRange(0, 5)
            dateMonth = dateMonth.removeRange(2, dateMonth.length)
            val dateDay = date.removeRange(0, 8)
            date =
                "$dateDay/$dateMonth/$dateYear" + " (${movie.production_countries[0]["iso_3166_1"]})"

            binding.movieReleaseDate.apply {
                text = date
            }

            val htmlText = "<font color=#ffffff>${movie.title}</font> <font color=#cce0e3>(${
                movie.release_date.removeRange(
                    4,
                    movie.release_date.length
                )
            })</font>"

            binding.movieTitle.apply {
                text = Html.fromHtml(htmlText)
            }
        } else {
            binding.movieTitle.apply {
                text = movie.title
            }
        }

        var genres: String? = ""
        for (element in movie.genres) genres += "${element["name"]}, "
        binding.movieGenres.apply {
            text = genres!!.substring(0, genres.length - 2)
        }

        /**
         * Formatting the runtime in pattern of "(int)h(int)m"
         */
        val runtimeStr = "${movie.runtime.toInt() / 60}h ${movie.runtime.toInt() % 60}m"
        if (movie.runtime.toInt() >= 60) {
            binding.movieRuntime.apply {
                text = runtimeStr
            }
        } else {
            binding.movieRuntime.apply {
                text = movie.runtime
            }
        }

        binding.movieTagline.apply {
            text = movie.tagline
        }

        binding.movieOverview.apply {
            text = movie.overview
        }

        /**
         * If there are no tagline or overview, they must be disappear. When they not, there is a huge amounts of space. That's why when they are not
         * exists, we set their visibility to View.GONE
         */
        if (movie.tagline == "") binding.movieTagline.visibility = View.GONE
        if (movie.overview == "") binding.movieHeadline.visibility = View.GONE
        else {
            binding.movieHeadline.apply {
                text = context.getString(R.string.overview)
            }
        }

        binding.movieMembers.apply {
            text = context.getString(R.string.members_vote)
        }

        binding.movieStars.apply {
            text = context.getString(R.string.cast)
        }

        binding.progressBar.apply {
            progress = (movie.vote_average * 10).toInt()
        }

        val progressStr: String = binding.progressBar.progress.toString() + "%"

        binding.progressText.apply {
            text = progressStr
        }

        if (movie.status == "Released") binding.movieStatus.setImageResource(R.drawable.ic_released)

        Glide.with(this@MovieDetailActivity).load(this@MovieDetailActivity.getString(R.string.w600h900) + movie.poster_path)
            .apply(RequestOptions().centerCrop())
            .into(binding.poster)

        Glide.with(this@MovieDetailActivity).load(this@MovieDetailActivity.getString(R.string.w600h900) + movie.backdrop_path)
            .apply(RequestOptions().centerCrop())
            .into(binding.bgPoster)
    }


    override fun onItemClicked(v: View, data: Actor) {
        /*val intent = Intent(this@MovieDetailActivity, ActorDetailActivity::class.java)
        intent.putExtra("actorid", data.id)
        startActivity(intent)*/
    }
}