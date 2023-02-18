package com.example.nytmovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nytmovies.databinding.MovieLayoutBinding
import com.example.nytmovies.models.Result
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MovieAdpter @Inject constructor(private val movies: List<Result>):
RecyclerView.Adapter<MovieAdpter.MoviewViewHolder>() {

lateinit var onItemClick: ((String) -> Unit)
inner class MoviewViewHolder(val binding: MovieLayoutBinding, val context: Context): RecyclerView.ViewHolder(binding.root) {
fun bind(
    movies : Result
){
    binding.movieTitle.text = movies.display_title
    binding.movieDescription.text = movies.summary_short
    Picasso.get().load(movies?.multimedia?.src).into(binding.movieImage)
    }

}
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviewViewHolder {
return MoviewViewHolder(
    MovieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent,false), parent.context
)
}
override fun onBindViewHolder(holder: MoviewViewHolder, position: Int) {
holder.bind (movies[position])
holder.itemView.setOnClickListener {
    onItemClick.invoke(movies[position].display_title)
}
}

override fun getItemCount(): Int = movies.size


}