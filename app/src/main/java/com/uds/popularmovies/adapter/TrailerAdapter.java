package com.uds.popularmovies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uds.popularmovies.R;
import com.uds.popularmovies.model.MovieTrailer;
import com.uds.popularmovies.utils.ItemClickListener;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private List<MovieTrailer> movieTrailers;
    private final ItemClickListener itemClickListener;

    public TrailerAdapter(List<MovieTrailer> movieTrailers, ItemClickListener itemClickListener) {
        this.movieTrailers = movieTrailers;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item, parent, false);
        return new ViewHolder(view);
    }

    public void setMovieTrailers(List<MovieTrailer> movieTrailers) {
        this.movieTrailers = movieTrailers;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieTrailer movieTrailer = movieTrailers.get(position);
        holder.trailerTitle.setText(movieTrailer.getName());
        Picasso.get().load(movieTrailer.getThumbnailUrl()).into(holder.posterThumbnail);

    }

    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView posterThumbnail,playButton;
        TextView trailerTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterThumbnail=itemView.findViewById(R.id.iv_poster_thumbnail);
            trailerTitle=itemView.findViewById(R.id.tv_trailer_name);
            playButton=itemView.findViewById(R.id.play_button);
            playButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getAdapterPosition());
        }
    }
}
