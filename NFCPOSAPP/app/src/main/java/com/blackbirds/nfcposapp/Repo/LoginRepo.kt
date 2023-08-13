package com.blackbirds.nfcposapp.Repo

import com.blackbirds.nfcposapp.Common.DataStatus
import com.blackbirds.nfcposapp.Model.LoginRequest
import com.blackbirds.nfcposapp.Network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepo @Inject constructor(private val apiService: ApiService) {
    suspend fun login(body: LoginRequest) = flow {
        emit(DataStatus.loading())

        val result = apiService.login(body)

        when (result.code()){
            200 -> emit(DataStatus.success(result.body()))
            400 -> emit(DataStatus.error(result.message()))
            404 -> emit(DataStatus.error(result.message()))
            500 -> emit(DataStatus.error(result.message()))
        }
    }
        .catch {
            emit(DataStatus.error(it.message.toString()))
        }
        .flowOn(Dispatchers.IO)
}