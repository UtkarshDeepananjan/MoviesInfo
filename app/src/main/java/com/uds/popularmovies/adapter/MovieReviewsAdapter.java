package com.uds.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uds.popularmovies.R;
import com.uds.popularmovies.model.AuthorDetails;
import com.uds.popularmovies.model.MovieReview;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {
    private List<MovieReview> movieReviews;
    private final boolean isDetailed;
    private final Context context;

    public MovieReviewsAdapter(Context context, List<MovieReview> reviews, boolean isDetailed) {
        movieReviews = reviews;
        this.isDetailed = isDetailed;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final boolean[] isExpanded = {false};
        MovieReview review = movieReviews.get(position);
        AuthorDetails authorDetails = review.getAuthorDetails();
        holder.rating.setText(String.valueOf(authorDetails.getRating()));
        holder.content.setText(review.getContent());
        holder.userName.setText(review.getAuthor());
        if (authorDetails.getAvatarPath() != null && authorDetails.getAvatarPath().length() > 0) {
            String avtarPath = authorDetails.getAvatarPath().substring(1);
            Picasso.get().load(avtarPath).placeholder(R.drawable.ic_avtar).error(R.drawable.ic_avtar).into(holder.avtar);
        }
        holder.viewMore.setOnClickListener(v -> {
            if (isExpanded[0]) {
                holder.content.setMaxLines(Integer.MAX_VALUE);
                holder.viewMore.setText(context.getString(R.string.view_less_label));
            } else {
                holder.content.setMaxLines(3);
                holder.viewMore.setText(context.getString(R.string.view_more_label));
            }
            isExpanded[0] = !isExpanded[0];
        });

    }

    @Override
    public int getItemCount() {
        return isDetailed ? movieReviews.size() : Math.min(movieReviews.size(), 5);
    }

    public void setMovieReviews(List<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final CircleImageView avtar;
        final TextView content;
        final TextView rating;
        final TextView userName;
        final TextView viewMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avtar = itemView.findViewById(R.id.iv_avtar);
            content = itemView.findViewById(R.id.tv_content);
            rating = itemView.findViewById(R.id.tv_rating);
            userName = itemView.findViewById(R.id.tv_user_name);
            viewMore = itemView.findViewById(R.id.tv_view_more);
        }
    }
}
