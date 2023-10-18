package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.response.StoryResponse
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _storyResponse = MutableLiveData<StoryResponse>()
    val storyResponse: LiveData<StoryResponse> = _storyResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getStories(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getStories(token)
                _storyResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun uploadImage(file: File, description: String) = repository.uploadImage(file, description)
}

