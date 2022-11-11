package com.blackbirds.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackbirds.kotlincoroutines.Adapter.PostListAdapter
import com.blackbirds.kotlincoroutines.Common.Common
import com.blackbirds.kotlincoroutines.Model.PostModel
import com.blackbirds.kotlincoroutines.Network.APIService
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await
import retrofit2.http.Tag

class MainActivity : AppCompatActivity() {

    var apiService: APIService? = null
    var recyclerPost: RecyclerView? = null
    var adapter: PostListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiService = Common.getAPIService

        recyclerPost = findViewById(R.id.recyclerPost)
        recyclerPost!!.layoutManager = LinearLayoutManager(this)

        getFetchData()
    }

    private fun getFetchData() {
        GlobalScope.launch(Dispatchers.Main){
            val posts = apiService!!.getPosts().await()
            if (posts != null){
                displayData(posts)
            }

        }

    }

    private fun displayData(posts: List<PostModel>) {
        adapter = PostListAdapter(this, posts)
        adapter!!.notifyDataSetChanged()
        recyclerPost!!.adapter = adapter
    }
}