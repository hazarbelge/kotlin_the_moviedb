package com.hazarbelge.themoviedb.views.main.ui.top_rated.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.views.main.ui.top_rated.adapter.TopRatedAdapter
import com.hazarbelge.themoviedb.views.main.ui.top_rated.viewmodel.TopRatedViewModel
import com.hazarbelge.themoviedb.base.BaseFragment
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.ui.dialog.ProgressDialog
import com.hazarbelge.themoviedb.databinding.FragmentTopRatedBinding
import com.hazarbelge.themoviedb.views.main.ui.movie_detail.view.MovieDetailActivity
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Result

class TopRatedFragment : BaseFragment<TopRatedViewModel, FragmentTopRatedBinding>(),
    ItemClickListener<Movie>, SwipeRefreshLayout.OnRefreshListener{

    private var progressDialog: ProgressDialog? = null

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
        binding.swipe.setOnRefreshListener(this)
        progressDialog = context?.let { ProgressDialog(it) }

        setFragmentTitle()
        getTopRatedMovies()
    }

    private fun setFragmentTitle() {
        binding.tView.apply {
            text = getString(R.string.top_rated)
        }
    }


    private fun getTopRatedMovies() {
        progressDialog?.show()
        viewModel.getTopRatedMovies().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    println("SuccessTopRatedMovies: $it")
                    if(it.data.results != null) {
                        val recyclerAdapter = TopRatedAdapter(this, it.data.results)
                        binding.recyclerview.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = recyclerAdapter
                        }
                    }
                }
                else -> {
                    println("ErrorTopRatedMovies: $it")
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
        getTopRatedMovies()
    }
}