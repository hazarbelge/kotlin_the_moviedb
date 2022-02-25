package com.hazarbelge.themoviedb.views.main.ui.latest.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.views.main.ui.latest.adapter.LatestAdapter
import com.hazarbelge.themoviedb.views.main.ui.latest.viewmodel.LatestViewModel
import com.hazarbelge.themoviedb.base.BaseFragment
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.databinding.FragmentLatestBinding
import com.hazarbelge.themoviedb.views.main.ui.movie_detail.view.MovieDetailActivity
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Result

class LatestFragment : BaseFragment<LatestViewModel, FragmentLatestBinding>(),
    ItemClickListener<Movie> {

    override val binding: FragmentLatestBinding by lazy {
        FragmentLatestBinding.inflate(
            layoutInflater
        )
    }

    companion object {
        fun newInstance() = LatestFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLatestMovie().observe(viewLifecycleOwner) {
            if (it is Result.Success) {
                println("succes: $it")
                val recyclerAdapter = LatestAdapter(this, it.data)
                binding.recyclerview.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = recyclerAdapter
                }
            } else {
                println("error: $it")
            }
        }

        binding.tView.apply {
            text = getString(R.string.latest)
        }
    }

    override fun onItemClicked(v: View, data: Movie) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("movieid", data.id)
        startActivity(intent)
    }
}