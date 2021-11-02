package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.HorizontalAdapter
import com.example.todolist.adapter.ViewPagerAdapter
import com.example.todolist.databinding.ActivityMainBinding
import Home
import android.content.Intent
import com.example.todolist.models.Photo
import com.example.todolist.services.RetrofitInstance
import kotlinx.android.synthetic.main.image_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.FrameLayout
import android.R
import android.graphics.Color

import androidx.appcompat.content.res.AppCompatResources

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat












class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : HorizontalAdapter
    private var page : Int = 1
    private var photos : MutableList<Photo> = ArrayList()


    private var sort : String = "popular"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ViewPagerAdapter(this)
        val tableNames = arrayOf("Home")
        adapter.addFragment(Home() , tableNames[0])
        binding.viewpagerId.adapter = adapter
        getImages()
        initRecyclerView()



    }

    private fun getImages() {
        page = 1
        val getPost = RetrofitInstance.api.getRecentPhotos(page,30,sort)
        getPost.enqueue(object : Callback<MutableList<Photo>> {
            override fun onResponse(
                call: Call<MutableList<Photo>>,
                response: Response<MutableList<Photo>>
            ) {
                if(response.isSuccessful)
                {
                    photos.clear()
                    Log.d("response",response.body().toString())
                    response.body()?.let { photos.addAll(it)}
                    adapter.notifyDataSetChanged()
                }
                else
                    Log.d("response",response.body().toString())
            }

            override fun onFailure(call: Call<MutableList<Photo>>, t: Throwable) {
                Log.d("Response","Failed")
            }

        })

    }

    private fun initRecyclerView() {
        val recycler = binding.recycler

        //setting recycler to horizontal scroll
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter = HorizontalAdapter(photos,applicationContext)
        binding.recycler.adapter = adapter
        adapter.setOnItemClickListener(object : HorizontalAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var intent = Intent(applicationContext, stories_activity::class.java)
                intent.putExtra("photo" , photos[position].url.regular)
                startActivity(intent);



            }
        })

    }


}



