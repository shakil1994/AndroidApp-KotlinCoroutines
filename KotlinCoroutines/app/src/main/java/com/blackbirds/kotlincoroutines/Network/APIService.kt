package com.blackbirds.kotlincoroutines.Network

import com.blackbirds.kotlincoroutines.Model.PostModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("posts")
    fun getPosts(): Call<List<PostModel>>
}