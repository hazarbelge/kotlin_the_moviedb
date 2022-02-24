package com.hazarbelge.themoviedb.ui.main.views.top_rated.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.ui.main.views.top_rated.adapter.TopRatedAdapter
import com.hazarbelge.themoviedb.ui.main.views.top_rated.viewmodel.TopRatedViewModel
import com.hazarbelge.themoviedb.base.BaseFragment
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.databinding.FragmentTopRatedBinding
import com.hazarbelge.themoviedb.ui.main.views.movie_detail.view.MovieDetailActivity
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Result
import com.hazarbelge.themoviedb.ui.main.views.top_rated.view.TopRatedFragment

class TopRatedFragment : BaseFragment<TopRatedViewModel, FragmentTopRatedBinding>(),
    ItemClickListener<Movie> {

    override val binding: FragmentTopRatedBinding by lazy {
        FragmentTopRatedBinding.inflate(
            layoutInflater
        )
    }

    companion object {
        fun newInstance() = TopRatedFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTopRatedMovies().observe(viewLifecycleOwner) {
            if (it is Result.Success && it.data.results != null) {
                println("succes: $it")
                val recyclerAdapter = TopRatedAdapter(this, it.data.results)
                binding.recyclerview.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = recyclerAdapter
                }
            } else {
                println("error: $it")
            }
        }

        binding.tView.apply {
            text = getString(R.string.top_rated)
        }
    }

    override fun onItemClicked(v: View, data: Movie) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("movieid", data.id)
        startActivity(intent)
    }
}