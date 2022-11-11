package com.blackbirds.kotlincoroutines.Common

import com.blackbirds.kotlincoroutines.Network.APIService
import com.blackbirds.kotlincoroutines.Network.RetrofitClient

object Common {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val getAPIService: APIService
        get() = RetrofitClient.getRetrofitClient(BASE_URL).create(APIService::class.java)
}