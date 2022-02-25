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
import com.hazarbelge.themoviedb.util.RecyclerViewScrollListener

class NowPlayingFragment : BaseFragment<NowPlayingViewModel, FragmentNowPlayingBinding>(),
    ItemClickListener<Movie>, SwipeRefreshLayout.OnRefreshListener, RecyclerViewScrollListener.ScrollCallback {

    private var allMovies = arrayListOf<Movie>()
    private var progressDialog: ProgressDialog? = null
    private var page: Int = 1
    private var totalResults: Int = -1
    private var isLoading: Boolean = false

    private val nowPlayingAdapter: NowPlayingAdapter = NowPlayingAdapter(this)
    private val mScrollListener by lazy { RecyclerViewScrollListener(this) }


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

        setRecyclerView()
        setFragmentTitle()
        getNowPlayingMovies(page = page)
    }

    private fun setRecyclerView() {
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = nowPlayingAdapter
            addOnScrollListener(mScrollListener)
        }
    }

    private fun setFragmentTitle() {
        binding.tView.apply {
            text = getString(R.string.now_playing)
        }
    }

    private fun getNowPlayingMovies(page: Int) {
        progressDialog?.show()
        viewModel.getNowPlayingMovies(page = page).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    println("SuccessNowPlayingMovies: $it")
                    if (it.data.results != null) {
                        totalResults = it.data.totalResults
                        allMovies.addAll(it.data.results)
                        nowPlayingAdapter.submitList(allMovies)
                        isLoading = false
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
        page = 1
        allMovies.clear()
        getNowPlayingMovies(page = page)
    }

    override fun onScrollCompleted(firstVisibleItem: Int, isLoadingMoreData: Boolean) {
        if (allMovies.size != totalResults) {
            if (!isLoading) {
                progressDialog!!.show()
                isLoading = true
                page = page.plus(1)
                getNowPlayingMovies(page = page)
            }
        }
    }
}