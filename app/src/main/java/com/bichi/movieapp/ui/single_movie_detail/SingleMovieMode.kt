package com.bichi.movieapp.ui.single_movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bichi.movieapp.data.repository.NetworkState
import com.bichi.movieapp.data.vo.MovieDetail
import io.reactivex.disposables.CompositeDisposable

class SingleMovieMode(
    private val movieRepository:MovieDetailRepository,
    movieId:Int
):ViewModel() {
    private val compositeDisposable=CompositeDisposable()

    val movieDetail : LiveData<MovieDetail> by lazy {
        movieRepository.fetchMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
       movieRepository.getMoveiDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}