package com.hazarbelge.themoviedb.ui.main.views.popular.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.ui.main.views.popular.adapter.PopularAdapter
import com.hazarbelge.themoviedb.base.BaseFragment
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.databinding.FragmentPopularBinding
import com.hazarbelge.themoviedb.ui.main.views.movie_detail.view.MovieDetailActivity
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Result
import com.hazarbelge.themoviedb.ui.main.views.popular.viewmodel.PopularViewModel

class PopularFragment : BaseFragment<PopularViewModel, FragmentPopularBinding>(),
    ItemClickListener<Movie> {

    override val binding: FragmentPopularBinding by lazy {
        FragmentPopularBinding.inflate(
            layoutInflater
        )
    }

    companion object {
        fun newInstance() = PopularFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPopularMovies().observe(viewLifecycleOwner) {
            if (it is Result.Success && it.data.results != null) {
                println("succes: $it")
                val recyclerAdapter = PopularAdapter(this, it.data.results)
                binding.recyclerview.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = recyclerAdapter
                }
            } else {
                println("error: $it")
            }
        }

        binding.tView.apply {
            text = getString(R.string.popular)
        }
    }

    override fun onItemClicked(v: View, data: Movie) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("movieid", data.id)
        startActivity(intent)
    }
}