package com.dicoding.picodiploma.loginwithanimation.view.story

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityListStoryBinding
import com.dicoding.picodiploma.loginwithanimation.di.Injection
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainViewModel

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storyAdapter = StoryAdapter(emptyList())

        binding.listItem.apply {
            layoutManager = LinearLayoutManager(this@ListStoryActivity)
            adapter = storyAdapter
        }

        val userRepository = Injection.provideRepository(this)
        viewModel = ViewModelProvider(this, ViewModelFactory(userRepository))[MainViewModel::class.java]

        viewModel.getSession().observe(this) { token ->
            if (token.token.isNotEmpty()) {
                Toast.makeText(this@ListStoryActivity, token.token, Toast.LENGTH_SHORT).show()
                viewModel.getStories(token.token)
            } else {
                // Handle the case where there is no token
                Toast.makeText(this@ListStoryActivity, "No valid token found.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.storyResponse.observe(this) { response ->
            response?.let {
                storyAdapter.updateData(it.listStory)
            }
        }
    }
}
