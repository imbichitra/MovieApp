package com.bichi.movieapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bichi.movieapp.data.api.TheMovieDbInterface
import com.bichi.movieapp.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService:TheMovieDbInterface,
    private val compositeDisposable: CompositeDisposable
):DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}