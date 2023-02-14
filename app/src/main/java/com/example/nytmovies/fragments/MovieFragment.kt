package com.example.nytmovies.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.Menu
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytmovies.R
import com.example.nytmovies.adapter.MovieAdpter
import com.example.nytmovies.databinding.FragmentMovieBinding
import com.example.nytmovies.models.Result
import com.example.nytmovies.utils.Status
import com.example.nytmovies.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()

    private lateinit var movieAdpter: MovieAdpter

    private lateinit var binding: FragmentMovieBinding

    var filteredMovies: List<Result> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false)

        return binding.root


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMovies()
    }


    //searchview
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterText(newText)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun onMovieClick() {
        movieAdpter.onItemClick = { item ->
            val action = MovieFragmentDirections.actionMovieFragmentToNewsDetailsFragment(item)
            requireView().findNavController().navigate(action)
        }
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
        ) { movieResponse ->
            when (movieResponse.status) {
                Status.SUCCESS -> {
                    val response = movieResponse.data?.results
                    //progressbar
                    binding.progressBar.visibility = View.GONE
                    response?.let { response ->
                        filteredMovies = response
                        movieAdpter = MovieAdpter(response)
                        binding.movierecyclerView.visibility = View.VISIBLE

                        setRecyclerView()
                        onMovieClick()
                    }
                }
                // if error state
                Status.ERROR -> {
                    // TODO Dismiss progress dialog
                    // TODO Show error message in dialog.
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), movieResponse.message, Toast.LENGTH_LONG)
                        .show()
                }
                // if still loading
                Status.LOADING -> {
                    // TODO Show progress dialog
                    binding.progressBar.visibility = View.VISIBLE
                    binding.movierecyclerView.visibility = View.GONE
                }
            }
        }
    }

    private fun filterText(text: String) {
        val theFilteredMovies: List<Result> = ArrayList()
        for (movie in filteredMovies) {
            if (movie.display_title.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))) {
                (theFilteredMovies as ArrayList<Result>).add(movie)
            }
        }
        movieAdpter = MovieAdpter(theFilteredMovies)
        binding.movierecyclerView.adapter = movieAdpter
        movieAdpter.notifyDataSetChanged()

    }

}

