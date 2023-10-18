package com.dicoding.picodiploma.loginwithanimation.view.story

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityListStoryBinding
import com.dicoding.picodiploma.loginwithanimation.di.Injection
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.camera.UploadFotoActivity
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity
import com.dicoding.picodiploma.loginwithanimation.view.main.MainViewModel
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userRepository = Injection.provideRepository(this)
        viewModel = ViewModelProvider(this, ViewModelFactory(userRepository))[MainViewModel::class.java]

        viewModel.getSession().observe(this) { user ->

            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }

            storyAdapter = StoryAdapter { storyItem ->
                val intent = Intent(this@ListStoryActivity, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.ID, storyItem.id)
                intent.putExtra(DetailStoryActivity.NAME, storyItem.name)
                intent.putExtra(DetailStoryActivity.DESCRIPTION, storyItem.description)
                intent.putExtra(DetailStoryActivity.PICTURE, storyItem.photoUrl)
                startActivity(intent)
            }

            binding.listItem.apply {
                layoutManager = LinearLayoutManager(this@ListStoryActivity)
                adapter = storyAdapter
            }

            viewModel.getStories(user.token)

            viewModel.storyResponse.observe(this) { response ->
                Log.d("Stories", "onCreate: $response")
                response?.let {
                    storyAdapter.submitList(it.listStory)
                }
            }
        }
        binding.fabAddStory.setOnClickListener{
            Intent (this,UploadFotoActivity::class.java )
        }

        binding.logout.setOnClickListener {
            Intent(this, MainActivity::class.java)
        }
    }
}
