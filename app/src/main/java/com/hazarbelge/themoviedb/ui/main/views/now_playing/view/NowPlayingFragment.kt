package com.hazarbelge.themoviedb.ui.main.views.now_playing.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.ui.main.views.now_playing.adapter.NowPlayingAdapter
import com.hazarbelge.themoviedb.ui.main.views.now_playing.viewmodel.NowPlayingViewModel
import com.hazarbelge.themoviedb.base.BaseFragment
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.databinding.FragmentNowPlayingBinding
import com.hazarbelge.themoviedb.ui.main.views.movie_detail.view.MovieDetailActivity
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Result

class NowPlayingFragment : BaseFragment<NowPlayingViewModel, FragmentNowPlayingBinding>(),
    ItemClickListener<Movie> {

    override val binding: FragmentNowPlayingBinding by lazy {
        FragmentNowPlayingBinding.inflate(
            layoutInflater
        )
    }

    companion object {
        fun newInstance() = NowPlayingFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovies(
            getString(R.string.api_key),
            getString(R.string.language),
        ).observe(viewLifecycleOwner) {
            if (it is Result.Success) {
                println("succes: $it")
                val recyclerAdapter = NowPlayingAdapter(this, it.data.results)
                binding.recyclerview.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = recyclerAdapter
                }
            } else {
                println("error: $it")
            }
        }

        binding.tView.apply {
            text = getString(R.string.now_playing)
        }
    }

    override fun onItemClicked(v: View, data: Movie) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("movieid", data.id)
        startActivity(intent)
    }
}