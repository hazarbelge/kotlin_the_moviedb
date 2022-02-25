package com.hazarbelge.themoviedb.views.main.views.upcoming.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.views.main.views.upcoming.adapter.UpcomingAdapter
import com.hazarbelge.themoviedb.views.main.views.upcoming.viewmodel.UpcomingViewModel
import com.hazarbelge.themoviedb.base.BaseFragment
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.ui.dialog.ProgressDialog
import com.hazarbelge.themoviedb.databinding.FragmentUpcomingBinding
import com.hazarbelge.themoviedb.views.main.views.movie_detail.view.MovieDetailActivity
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Result

class UpcomingFragment : BaseFragment<UpcomingViewModel, FragmentUpcomingBinding>(),
    ItemClickListener<Movie>, SwipeRefreshLayout.OnRefreshListener{

    private var progressDialog: ProgressDialog? = null

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
        binding.swipe.setOnRefreshListener(this)
        progressDialog = context?.let { ProgressDialog(it) }

        setFragmentTitle()
        getUpcomingMovies()
    }

    private fun setFragmentTitle() {
        binding.tView.apply {
            text = getString(R.string.upcoming)
        }
    }


    private fun getUpcomingMovies() {
        progressDialog?.show()
        viewModel.getUpcomingMovies().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    println("SuccessUpcomingMovies: $it")
                    if (it.data.results != null) {
                        val recyclerAdapter = UpcomingAdapter(this, it.data.results)
                        binding.recyclerview.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = recyclerAdapter
                        }
                    }
                }
                else -> {
                    println("ErrorUpcomingMovies: $it")
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
        getUpcomingMovies()
    }
}