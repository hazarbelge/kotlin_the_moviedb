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
import com.hazarbelge.themoviedb.common.ProgressDialog
import com.hazarbelge.themoviedb.databinding.ActivityMovieDetailBinding
import com.hazarbelge.themoviedb.network.model.Actor
import com.hazarbelge.themoviedb.ui.main.views.movie_detail.adapter.CastAdapter
import com.hazarbelge.themoviedb.ui.main.views.movie_detail.viewmodel.MovieDetailViewModel
import com.hazarbelge.themoviedb.network.model.Crew
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Result

class MovieDetailActivity : BaseActivity<MovieDetailViewModel, ActivityMovieDetailBinding>(),
    ItemClickListener<Actor> {

    private var progressDialog: ProgressDialog? = null

    override val binding: ActivityMovieDetailBinding by lazy {
        ActivityMovieDetailBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)

        setSupportActionBar(binding.topNavBar.toolbar)
        setToolbarActions()

        val position = intent.getStringExtra("movieid")
        getMovie(position!!)
        getCast(position)
        getCrew(position)
    }

    private fun setToolbarActions() {
        binding.topNavBar.apply {
            title = ""
            returnImageView.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun getMovie(movieID: String) {
        progressDialog?.show()
        viewModel.getMovieById(movieID).observe(this) {
            when (it) {
                is Result.Success -> {
                    movieDetailResponseHandler(it.data)
                }
                else -> {

                }
            }
            if (progressDialog?.isShowing == true) {
                progressDialog?.dismiss()
            }
        }
    }

    private fun getCast(movieID: String) {
        progressDialog?.show()
        viewModel.getCast(movieID).observe(this) {
            when (it) {
                is Result.Success -> {
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
                else -> {

                }
            }
            if (progressDialog?.isShowing == true) {
                progressDialog?.dismiss()
            }
        }
    }

    private fun getCrew(movieID: String) {
        viewModel.getCrew(movieID).observe(this) {
            when (it) {
                is Result.Success -> {
                    responseHandlerCrew(it.data)
                }
                else -> {

                }
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
        if (movie.release_date.isNotEmpty()) {
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

        binding.movieOverview.apply {
            text = movie.overview
        }

        if (movie.tagline.isEmpty()) binding.movieTagline.visibility = View.GONE
        else {
            binding.movieTagline.apply {
                text = movie.tagline
            }
        }

        if (movie.overview.isEmpty()) binding.movieHeadline.visibility = View.GONE
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

        Glide.with(this@MovieDetailActivity)
            .load(this@MovieDetailActivity.getString(R.string.w600h900) + movie.poster_path)
            .apply(RequestOptions().centerCrop())
            .into(binding.poster)

        Glide.with(this@MovieDetailActivity)
            .load(this@MovieDetailActivity.getString(R.string.w600h900) + movie.backdrop_path)
            .apply(RequestOptions().centerCrop())
            .into(binding.bgPoster)
    }


    override fun onItemClicked(v: View, data: Actor) {
        /*val intent = Intent(this@MovieDetailActivity, ActorDetailActivity::class.java)
        intent.putExtra("actorid", data.id)
        startActivity(intent)*/
    }
}