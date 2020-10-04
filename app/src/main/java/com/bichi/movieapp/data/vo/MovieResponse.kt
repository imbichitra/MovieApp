package com.bichi.movieapp.data.vo

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    val page : Int,
    val movies:List<Movie>,
    @SerializedName("total_pages")
    val totalPages:Int,
    @SerializedName("total_results")
    val totalResult:Int
)