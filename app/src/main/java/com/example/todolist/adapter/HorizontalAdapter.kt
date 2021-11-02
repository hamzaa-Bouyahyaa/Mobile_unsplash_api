package com.example.todolist.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todolist.R
import com.example.todolist.models.Photo


class HorizontalAdapter(private val photos: MutableList<Photo>, private val context: Context) :
    RecyclerView.Adapter<HorizontalAdapter.MyView>() {

    private lateinit var  mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int){
        }
    }

    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }


    class MyView(itemView : View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val story : ImageView = itemView.findViewById(R.id.imageStory)
        val username : TextView = itemView.findViewById(R.id.user)

        fun bindView(photo: Photo){
            username.text = photo.user.username;

        }
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return MyView(inflatedView,mListener)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        Glide.with(context)
            .load(photos[position].user.profileImage.large)
            .placeholder(ColorDrawable(Color.parseColor(photos[position].color)))
            .into(holder.story)

        holder.bindView(photos[position])

    }

    override fun getItemCount(): Int {
        return photos.size
    }

}