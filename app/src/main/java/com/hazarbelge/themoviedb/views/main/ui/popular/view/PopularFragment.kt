package com.hazarbelge.themoviedb.views.main.ui.popular.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.views.main.ui.popular.adapter.PopularAdapter
import com.hazarbelge.themoviedb.base.BaseFragment
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.ui.dialog.ProgressDialog
import com.hazarbelge.themoviedb.databinding.FragmentPopularBinding
import com.hazarbelge.themoviedb.views.main.ui.movie_detail.view.MovieDetailActivity
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Result
import com.hazarbelge.themoviedb.views.main.ui.popular.viewmodel.PopularViewModel

class PopularFragment : BaseFragment<PopularViewModel, FragmentPopularBinding>(),
    ItemClickListener<Movie>, SwipeRefreshLayout.OnRefreshListener{

    private var progressDialog: ProgressDialog? = null

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
        binding.swipe.setOnRefreshListener(this)
        progressDialog = context?.let { ProgressDialog(it) }

        setFragmentTitle()
        getPopularMovies()
    }

    private fun setFragmentTitle() {
        binding.tView.apply {
            text = getString(R.string.popular)
        }
    }


    private fun getPopularMovies() {
        progressDialog?.show()
        viewModel.getPopularMovies().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    println("SuccessPopularMovies: $it")
                    if(it.data.results != null) {
                        val recyclerAdapter = PopularAdapter(this, it.data.results)
                        binding.recyclerview.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = recyclerAdapter
                        }
                    }
                }
                else -> {
                    println("ErrorPopularMovies: $it")
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
        getPopularMovies()
    }
}