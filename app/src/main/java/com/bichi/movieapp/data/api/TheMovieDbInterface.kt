package com.bichi.movieapp.data.api

import com.bichi.movieapp.data.vo.MovieDetail
import com.bichi.movieapp.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page:Int):Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")id:Int):Single<MovieDetail>
}