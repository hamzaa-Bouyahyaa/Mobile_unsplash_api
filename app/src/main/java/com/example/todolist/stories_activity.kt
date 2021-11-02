package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.example.todolist.databinding.ActivityStoriesBinding
import jp.shts.android.storiesprogressview.StoriesProgressView

class stories_activity : AppCompatActivity() {
    private lateinit var binding: ActivityStoriesBinding
    private lateinit var storiesProgressView: StoriesProgressView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        storiesProgressView = binding.stories;
        imageView = binding.image;

        storiesProgressView.setStoriesCount(1);
        storiesProgressView.setStoryDuration(1500L);
        storiesProgressView.startStories()

        imageView.setOnClickListener(){
            storiesProgressView.skip()
        }

        val photo = intent.getStringExtra("photo")

        Glide.with(applicationContext)
            .load(photo)
            .into(imageView)


    }


}