package com.example.movietrendr

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import org.w3c.dom.Text

class MovieAdapter(private val movieURLList: List<String>,
                   private val movieTitleList: List<String>,
                   private val movieRatingList: List<String>,
                   private val movieDateList: List<String>
                   ):RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val movieImage: ImageView
        val movieTitle: TextView
        val movieRating: TextView
        val movieDate: TextView

        init {
            movieImage = view.findViewById(R.id.ivMovie)
            movieTitle = view.findViewById(R.id.tvTitle)
            movieRating = view.findViewById(R.id.tvRating)
            movieDate = view.findViewById(R.id.tvReleaseDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_movie, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieURLList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView).load(movieURLList[position]).centerCrop().into(holder.movieImage)
        holder.movieTitle.setText(movieTitleList[position])
        holder.movieRating.setText(movieRatingList[position])
        holder.movieDate.setText(movieDateList[position])

        var moviePosition = position + 1
        var moviesSize = movieURLList.size

        holder.movieImage.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Movie $moviePosition/$moviesSize selected", Toast.LENGTH_SHORT).show()


        }

    }

}