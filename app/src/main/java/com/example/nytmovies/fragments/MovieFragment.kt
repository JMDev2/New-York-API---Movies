package com.example.nytmovies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytmovies.adapter.MovieAdpter
import com.example.nytmovies.databinding.FragmentMovieBinding
import com.example.nytmovies.utils.Status
import com.example.nytmovies.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdpter: MovieAdpter

    private lateinit var binding: FragmentMovieBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false)

        return binding.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMovies()
    }

    private fun setRecyclerView() {
        binding.movierecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdpter
        }
    }

    private fun observeMovies() {
        viewModel.observeMovieLiveData().observe(
            viewLifecycleOwner
        ){ movieResponse ->
            when (movieResponse.status){
                Status.SUCCESS ->{
                    val response = movieResponse.data?.results
                    //progressbar
                    response?.let { response
                        movieAdpter = MovieAdpter(response)
                        //binding.movierecyclerView.visibility = View.VISIBLE


                        setRecyclerView()
                    }
                }
                // if error state
                Status.ERROR -> {
                    // TODO Dismiss progress dialog
                    // TODO Show error message in dialog.
                   // binding.progressBar2.visibility = View.GONE
                    Toast.makeText(requireContext(), movieResponse.message, Toast.LENGTH_LONG)
                        .show()
                }
                // if still loading
                Status.LOADING -> {
                    // TODO Show progress dialog
                   // binding.progressBar2.visibility = View.VISIBLE
                   // binding.movierecyclerView.visibility = View.GONE
                }
            }
        }
    }
}

