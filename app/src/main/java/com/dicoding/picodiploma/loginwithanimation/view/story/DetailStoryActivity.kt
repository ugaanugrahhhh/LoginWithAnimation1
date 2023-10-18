package com.dicoding.picodiploma.loginwithanimation.view.story

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class)
class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance()
    }
    private val userPreference: UserPreference by lazy {
        UserPreference.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra(ID)?: ""
        name = intent.getStringExtra(NAME)?: ""
        description = intent.getStringExtra(DESCRIPTION)?: ""
        picture = intent.getStringExtra(PICTURE)?: ""

        binding.tvDetailName.text = name
        binding.tvDetailDescription.text = description

        Glide.with(this).load(picture).into(binding.ivDetailPhoto)
    }

    companion object {
        const val ID = "ID"
        const val NAME = "NAME"
        const val DESCRIPTION = "DESCRIPTION"
        const val PICTURE = "PICTURE"

        var id: String = ""
        var name: String = ""
        var description: String? = null
        var picture: String? = null
    }
}