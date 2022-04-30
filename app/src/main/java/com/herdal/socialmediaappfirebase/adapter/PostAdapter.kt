package com.herdal.socialmediaappfirebase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herdal.socialmediaappfirebase.databinding.PostRowBinding
import com.herdal.socialmediaappfirebase.model.Post

class PostAdapter(private val postList: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    inner class PostViewHolder(val binding: PostRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.recyclerTextViewUserEmail.text = postList.get(position).email
        holder.binding.recyclerTextViewUserComment.text = postList.get(position).comment
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}