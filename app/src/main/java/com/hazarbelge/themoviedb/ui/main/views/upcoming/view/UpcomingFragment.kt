package com.hazarbelge.themoviedb.ui.main.views.upcoming.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.ui.main.views.upcoming.adapter.UpcomingAdapter
import com.hazarbelge.themoviedb.ui.main.views.upcoming.viewmodel.UpcomingViewModel
import com.hazarbelge.themoviedb.base.BaseFragment
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.databinding.FragmentUpcomingBinding
import com.hazarbelge.themoviedb.ui.main.views.movie_detail.view.MovieDetailActivity
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Result

class UpcomingFragment : BaseFragment<UpcomingViewModel, FragmentUpcomingBinding>(),
    ItemClickListener<Movie> {

    override val binding: FragmentUpcomingBinding by lazy {
        FragmentUpcomingBinding.inflate(
            layoutInflater
        )
    }

    companion object {
        fun newInstance() = UpcomingFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUpcomingMovies().observe(viewLifecycleOwner) {
            if (it is Result.Success && it.data.results != null) {
                println("succes: $it")
                val recyclerAdapter = UpcomingAdapter(this, it.data.results)
                binding.recyclerview.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = recyclerAdapter
                }
            } else {
                println("error: $it")
            }
        }

        binding.tView.apply {
            text = getString(R.string.upcoming)
        }
    }

    override fun onItemClicked(v: View, data: Movie) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("movieid", data.id)
        startActivity(intent)
    }
}