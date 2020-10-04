package com.bichi.movieapp.ui.single_movie_detail

import androidx.lifecycle.LiveData
import com.bichi.movieapp.data.api.TheMovieDbInterface
import com.bichi.movieapp.data.repository.MovieDetailsNetworkDataSource
import com.bichi.movieapp.data.repository.NetworkState
import com.bichi.movieapp.data.vo.MovieDetail
import io.reactivex.disposables.CompositeDisposable

class MovieDetailRepository(private val apiService:TheMovieDbInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchMovieDetails(compositeDisposable: CompositeDisposable,moveiId:Int):LiveData<MovieDetail>{
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(moveiId)
        return movieDetailsNetworkDataSource.downloadedMovieDetailResponse
    }

    fun getMoveiDetailNetworkState():LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
}