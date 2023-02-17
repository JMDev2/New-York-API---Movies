package com.example.nytmovies.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.nytmovies.R
import com.example.nytmovies.activities.BookmarksActivity
import com.example.nytmovies.activities.LaunchActivity
import com.example.nytmovies.api.MovieApiService
import com.example.nytmovies.databinding.FragmentNewsDetailsBinding
import com.example.nytmovies.models.Result
import com.example.nytmovies.utils.Status
import com.example.nytmovies.viewmodel.MovieViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private lateinit var item: Result
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieApiService: MovieApiService
    private val args: NewsDetailsFragmentArgs by navArgs()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        binding.bookmark.setOnClickListener {
            openBookmarkActivity()
        }

        return binding.root
    }
    fun openBookmarkActivity(){
        val intent = Intent(activity, BookmarksActivity::class.java)
        startActivity(intent)

    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMovieDetails(args.itemId) //receiving the args

    }

        fun getMovieDetails(title: String){
            viewModel.getMovies(title)
            viewModel.observeMovieLiveData().observe(
                viewLifecycleOwner
            ){ movies ->
                when (movies.status) {
                    Status.SUCCESS -> {
                        //TODO Dismiss progress dialog
                        binding.progressBar2.visibility = View.GONE
                        val response = movies.data?.results

                        response?.let {
                            binding.newFragment.visibility = View.VISIBLE

                            var response = movies.data.results.find { result ->
                                result.display_title == title
                            }

                                binding.titleDestails.text = response?.display_title
                                binding.descriptionDestails.text = response?.summary_short
                                Picasso.get().load(response?.multimedia?.src).into(binding.imageView)
                                binding.url.text = response?.link?.url

                                if (response?.mpaa_rating?.isNotEmpty() == true){
                                    binding.mpaaRating.text = response?.mpaa_rating

                                }else{
                                    binding.mpaaRating.text = "No Rating"
                                }

                                binding.publicationDate.text = response?.publication_date

                            binding.save.setOnClickListener {

                                val database = FirebaseDatabase.getInstance().reference


                                database.child("result").child("result").push().setValue(response)

                                Toast.makeText(context, "Movie Saved", Toast.LENGTH_SHORT).show()

                            }
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









