package com.bichi.movieapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bichi.movieapp.data.api.TheMovieDbInterface
import com.bichi.movieapp.data.vo.MovieDetail
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource(
    private val apiService:TheMovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()

    val networkState:LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetailResponse = MutableLiveData<MovieDetail>()
    val downloadedMovieDetailResponse:LiveData<MovieDetail>
        get() = _downloadedMovieDetailResponse

    fun fetchMovieDetails(movieId:Int){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                        }
                    )
            )
        }catch (e:Exception){
            Log.e(TAG, "fetchMovieDetails: "+e.message )
        }
    }

    companion object{
        val TAG = MovieDetailsNetworkDataSource::class.java.simpleName
    }
}