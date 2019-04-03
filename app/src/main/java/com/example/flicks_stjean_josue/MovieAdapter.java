package com.example.flicks_stjean_josue;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.flicks_stjean_josue.models.Config;
import com.example.flicks_stjean_josue.models.Movie;
import java.util.ArrayList;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    // List of movie
    ArrayList<Movie> movies;
    // config needed for image url
    Config config;
    // context for rendering
    Context context;

    // initialize with list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }
    public void setConfig(Config config) {
        this.config = config;
    }

    // creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get the context and create the inflate
        context =  parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // creates the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        //return a new ViewHolder
        return new ViewHolder(movieView);
    }
    // binds and inflated view to a new item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    // get the movie data at specified position
    Movie movie = movies.get(position);
    //populate the view with the movie date
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());
        // build url for poster image
        String imageUrl = config.getImageUrl(config.getPosterSize(),movie.getPosterPath());

        // load image using glide
       Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context,25,0))
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(holder.ivPosterImage);
    }
    // returns the total number of the items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // create the viewholder as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder{

    // Track view objects
    ImageView ivPosterImage;
    TextView tvTitle;
    TextView tvOverview;

    public ViewHolder(View itemView) {
    super(itemView);
    //lookup view objects by id
        ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
        tvOverview = itemView.findViewById(R.id.tvOverview);
        tvTitle = itemView.findViewById(R.id.tvTitle);

        }
    }}
