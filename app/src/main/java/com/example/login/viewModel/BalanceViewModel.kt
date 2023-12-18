package com.example.login.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.login.repository.UserRepository
import com.example.login.response.*
import kotlinx.coroutines.launch

class BalanceViewModel(application: Application) : AndroidViewModel(application) {

    val userRepo = UserRepository()
    val balanceResult: MutableLiveData<BaseResponse<BalaceResponse>> = MutableLiveData()

    fun getBalance(userId: String) {

        balanceResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val balanceRequest = BalanceRequest(userId)
                val response = userRepo.balanceUser(userId)
                if (response?.code() == 200) {
                    balanceResult.value = BaseResponse.Success(response.body())
                } else {
                    balanceResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                balanceResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}