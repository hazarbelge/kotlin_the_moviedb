package com.hazarbelge.themoviedb.ui.main.views.now_playing.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.base.BaseFragment
import com.hazarbelge.themoviedb.util.ItemClickListener
import com.hazarbelge.themoviedb.widget.dialog.ProgressDialog
import com.hazarbelge.themoviedb.databinding.FragmentNowPlayingBinding
import com.hazarbelge.themoviedb.ui.main.views.movie_detail.view.MovieDetailActivity
import com.hazarbelge.themoviedb.model.Movie
import com.hazarbelge.themoviedb.model.Result
import com.hazarbelge.themoviedb.ui.main.views.now_playing.adapter.NowPlayingAdapter
import com.hazarbelge.themoviedb.ui.main.views.now_playing.viewmodel.NowPlayingViewModel

class NowPlayingFragment : BaseFragment<NowPlayingViewModel, FragmentNowPlayingBinding>(),
    ItemClickListener<Movie>, SwipeRefreshLayout.OnRefreshListener{

    private var progressDialog: ProgressDialog? = null

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
        binding.swipe.setOnRefreshListener(this)
        progressDialog = context?.let { ProgressDialog(it) }

        setFragmentTitle()
        getNowPlayingMovies()
    }

    private fun setFragmentTitle() {
        binding.tView.apply {
            text = getString(R.string.now_playing)
        }
    }


    private fun getNowPlayingMovies() {
        progressDialog?.show()
        viewModel.getNowPlayingMovies().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    println("SuccessNowPlayingMovies: $it")
                    if(it.data.results != null) {
                        val recyclerAdapter = NowPlayingAdapter(this, it.data.results)
                        binding.recyclerview.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = recyclerAdapter
                        }
                    }
                }
                else -> {
                    println("ErrorNowPlayingMovies: $it")
                }
            }
            if (progressDialog?.isShowing == true) {
                progressDialog?.dismiss()
            }
            binding.swipe.isRefreshing = false
        }
    }

    override fun onItemClicked(v: View, data: Movie) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("movieid", data.id)
        startActivity(intent)
    }

    override fun onRefresh() {
        getNowPlayingMovies()
    }
}