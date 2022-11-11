package com.blackbirds.kotlincoroutines.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.blackbirds.kotlincoroutines.Model.PostModel
import com.blackbirds.kotlincoroutines.R

class PostListAdapter(var context: Context, var postModelList: List<PostModel>) :
    RecyclerView.Adapter<PostListAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var txtUserId : AppCompatTextView
        var txtBody : AppCompatTextView
        var txtTitle : AppCompatTextView

        init {
            txtUserId = itemView.findViewById(R.id.txtUserId)
            txtBody = itemView.findViewById(R.id.txtBody)
            txtTitle = itemView.findViewById(R.id.txtTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_post_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtTitle.text = postModelList[position].title.toString()
        holder.txtBody.text = StringBuilder(postModelList[position].body!!.substring(0, 20))
            .append("...").toString()
        holder.txtUserId.text = postModelList[position].userId.toString()

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Clicked: $position", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return postModelList.size
    }
}