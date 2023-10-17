package com.dicoding.picodiploma.loginwithanimation.view.story

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.StoryItem
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val userPreference: UserPreference by lazy {
        UserPreference.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            val token = getTokenFromUserPreference()
            val storyId = intent.getIntExtra(EXTRA_STORY_ID, 0)
            viewModel.getDetail(token, storyId)
            viewModel.detailResponse.observe(this@DetailStoryActivity) { response ->
                if (response != null) {
                    val story = response.message // Replace this with your actual data model
                    val storyItem = StoryItem
                } else {
                    // Handle the case when the response is null
                }
            }
        }
    }

    private fun updateUI(story: StoryItem) {
        binding.apply {
            tvDetailName.text = story.name
            tvDetailDescription.text = story.description

            Glide.with(binding.root)
                .load(story.photoUrl)
                .into(ivDetailPhoto)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private suspend fun getTokenFromUserPreference(): String {
        var token = ""
        userPreference.getSession().collect { user ->
            token = user.token
        }
        return token
    }

    companion object {
        const val EXTRA_STORY_ID = "extra_story_id"
    }
}
