package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.response.DetailResponse
import com.dicoding.picodiploma.loginwithanimation.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.response.StoryResponse
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _hasilRegister = MutableLiveData<RegisterResponse>()
    val hasilRegister: LiveData<RegisterResponse> = _hasilRegister

    private val _storyResponse = MutableLiveData<StoryResponse>()
    val storyResponse: LiveData<StoryResponse> = _storyResponse

    private val _detailResponse = MutableLiveData<DetailResponse>()
    val detailResponse: LiveData<DetailResponse> = _detailResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _hasilRegister.value = repository.register(name, email, password)
        }
    }

    fun getStories(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getStories("Bearer $token")
                _storyResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun getDetail(token: String, storyId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getDetail(token)
                _detailResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
