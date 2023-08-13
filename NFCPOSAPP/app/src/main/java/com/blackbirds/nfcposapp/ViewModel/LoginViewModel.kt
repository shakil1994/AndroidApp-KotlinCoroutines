package com.blackbirds.nfcposapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackbirds.nfcposapp.Common.DataStatus
import com.blackbirds.nfcposapp.Common.SingleLiveEvent
import com.blackbirds.nfcposapp.Model.LoginRequest
import com.blackbirds.nfcposapp.Model.LoginResponse
import com.blackbirds.nfcposapp.Repo.LoginRepo
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: LoginRepo): ViewModel() {
    private val _loginResponse = MutableLiveData<SingleLiveEvent<DataStatus<LoginResponse>>>()
    val loginResponse : LiveData<SingleLiveEvent<DataStatus<LoginResponse>>>
        get() = _loginResponse

    fun login(body: LoginRequest) = viewModelScope.launch {
        repo.login(body).collect{
            _loginResponse.value = SingleLiveEvent(it)
        }
    }
}