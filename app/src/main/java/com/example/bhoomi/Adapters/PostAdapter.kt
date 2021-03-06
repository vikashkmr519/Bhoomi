package com.example.bhoomi.Adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bhoomi.Data.Posts
import com.example.bhoomi.R
import com.squareup.picasso.Picasso
import androidx.recyclerview.widget.RecyclerView.ViewHolder as ViewHolder

class PostAdapter(private var itemlist : List<Posts>):
    RecyclerView.Adapter<PostAdapter.MyViewHolder>() {


    inner class MyViewHolder(view : View): RecyclerView.ViewHolder(view){
        var usernameTv = view.findViewById<TextView>(R.id.username)
        var image =  view.findViewById<ImageView>(R.id.postImage)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = itemlist[position]
        holder.usernameTv?.text = item.username
        Picasso.with(holder.image.context).load(item.image).into(holder.image);
    }

    override fun getItemCount(): Int {
       return itemlist.size

    }




}
