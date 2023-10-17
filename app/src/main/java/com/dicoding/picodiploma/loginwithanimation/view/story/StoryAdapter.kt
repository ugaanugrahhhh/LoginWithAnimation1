package com.dicoding.picodiploma.loginwithanimation.view.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.databinding.ItemStoryBinding
import com.dicoding.picodiploma.loginwithanimation.response.ListStoryItem

class StoryAdapter(private var listStory: List<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    fun updateData(newListStory: List<ListStoryItem>) {
        this.listStory = newListStory
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int {
        return listStory.size
    }

    inner class StoryViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListStoryItem) {
            Glide.with(binding.root.context)
                .load(item.photoUrl)
                .into(binding.ivItemPhoto)
            binding.tvItemName.text = item.name
            binding.tvItemDesc.text = item.description
        }
    }
}
