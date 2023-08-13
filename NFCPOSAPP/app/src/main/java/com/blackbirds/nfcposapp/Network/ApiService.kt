package com.blackbirds.nfcposapp.Network

import com.blackbirds.nfcposapp.Model.LoginRequest
import com.blackbirds.nfcposapp.Model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login/")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<LoginResponse>
}