package com.example.nytmovies

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.nytmovies.adapter.MovieAdpter
import com.example.nytmovies.api.MovieApiImpl
import com.example.nytmovies.api.MovieApiService
import com.example.nytmovies.databinding.FragmentNewsDetailsBinding
import com.example.nytmovies.models.MovieResponse
import com.example.nytmovies.models.Result
import com.example.nytmovies.utils.Status
import com.example.nytmovies.viewmodel.MovieViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call

@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private lateinit var item: Result
    private val viewModel: MovieViewModel by viewModels()

    private lateinit var movieApiService: MovieApiService

    private val args: NewsDetailsFragmentArgs by navArgs()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMovieDetails(args.itemId)




    }


        fun getMovieDetails(title: String){
            viewModel.getMovies(title)
            viewModel.observeMovieLiveData().observe(
                viewLifecycleOwner
            ){ movies ->
                when (movies.status) {
                    Status.SUCCESS -> {
                        //TODO Dismiss progress dialog

                        val response = movies.data?.results
                        binding.progressBar2.visibility = View.GONE

                        response?.let {
                            binding.newFragment.visibility = View.VISIBLE

                            var response = movies.data.results.find { result ->
                                result.display_title == title
                            }
                                binding.titleDestails.text = response?.display_title
                                binding.descriptionDestails.text = response?.summary_short
                                Picasso.get().load(response?.multimedia?.src).into(binding.imageView)


                        }
                    }
                    Status.ERROR -> {
                        binding.progressBar2.visibility = View.GONE
                        Toast.makeText(requireContext(), movies.message, Toast.LENGTH_LONG)
                            .show()
                    }
                    Status.LOADING -> {
                        // TODO Show progress dialog
                        binding.progressBar2.visibility = View.VISIBLE
                        binding.newFragment.visibility = View.GONE
                    }
                }
            }
        }
}




