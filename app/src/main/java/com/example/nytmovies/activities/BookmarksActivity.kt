package com.example.nytmovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytmovies.R
import com.example.nytmovies.adapter.FirebaseAdapter
import com.example.nytmovies.adapter.MovieAdpter
import com.example.nytmovies.databinding.ActivityBookmarksBinding
import com.google.firebase.database.*

class BookmarksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarksBinding
    private lateinit var movieList: ArrayList<com.example.nytmovies.models.Result>
    private lateinit var adapter: FirebaseAdapter
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.bookmarkRecyclerview.layoutManager = linearLayoutManager
        movieList = ArrayList()
        adapter = FirebaseAdapter(this@BookmarksActivity, movieList)
        binding.bookmarkRecyclerview.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("result").child("result")



        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                movieList.clear()
                for (itemSnapshot in snapshot.children){
                    val movies = itemSnapshot.getValue(com.example.nytmovies.models.Result::class.java)
                    if (movies != null){
                        movieList.add(movies)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }
}