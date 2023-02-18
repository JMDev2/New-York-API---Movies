package com.example.nytmovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nytmovies.R
import com.example.nytmovies.adapter.FirebaseAdapter
import com.example.nytmovies.adapter.MovieAdpter
import com.example.nytmovies.adapter.SwipeToDeleteCallback
import com.example.nytmovies.databinding.ActivityBookmarksBinding
import com.google.firebase.database.*

class BookmarksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarksBinding
    private lateinit var movieList: ArrayList<com.example.nytmovies.models.Result>
    private lateinit var adapter: FirebaseAdapter
    private lateinit var adpter: MovieAdpter
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarksBinding.inflate(layoutInflater)

        setContentView(binding.root)
        onSwipToDelete() //calling the swipe to delete method

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.bookmarkRecyclerview.layoutManager = linearLayoutManager
        movieList = ArrayList()
        adapter = FirebaseAdapter(this@BookmarksActivity, movieList)

        binding.bookmarkRecyclerview.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("result").child("result")

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                movieList.clear()
                for (itemSnapshot in snapshot.children) {
                    val movies =
                        itemSnapshot.getValue(com.example.nytmovies.models.Result::class.java)
                    if (movies != null) {
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


    //adding the onswipe delete method
    private fun onSwipToDelete() {

        val swipeDelete = object  : SwipeToDeleteCallback(){
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int)
            {
                adapter.deleteItem(viewHolder.adapterPosition)

            }

        }
        val touchHelper = ItemTouchHelper(swipeDelete)
        touchHelper.attachToRecyclerView(binding.bookmarkRecyclerview)
    }


}