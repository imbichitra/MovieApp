package com.bichi.movieapp.ui.single_movie_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bichi.movieapp.R
import com.bichi.movieapp.data.api.MovieDBClient
import com.bichi.movieapp.data.api.POSTER_BASE_URL
import com.bichi.movieapp.data.api.TheMovieDbInterface
import com.bichi.movieapp.data.repository.NetworkState
import com.bichi.movieapp.data.vo.MovieDetail
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewMode: SingleMovieMode
    private lateinit var movieRepository:MovieDetailRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId:Int = intent.getIntExtra("id",1)
        val apiService :TheMovieDbInterface = MovieDBClient.getClient()
        movieRepository = MovieDetailRepository(apiService)

        viewMode = getViewModel(movieId)

        viewMode.movieDetail.observe(this, Observer {
            bindUI(it)
        })

        viewMode.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    private fun bindUI(it: MovieDetail) {
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        //movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString()
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)
    }

    private fun getViewModel(movieId:Int):SingleMovieMode{
        return ViewModelProvider(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingleMovieMode(movieRepository,movieId) as T
            }
        })[SingleMovieMode::class.java]
    }


}