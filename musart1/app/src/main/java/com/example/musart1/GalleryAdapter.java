package com.example.musart1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private final Context context;
    private final int[][] imageSets;
    private final float[][] ratings;


    public GalleryAdapter(Context context, int[][] imageSets, float[][] ratings) {
        this.context = context;
        this.imageSets = imageSets;
        this.ratings = ratings;
    }



    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        int[] images = imageSets[position];
        float[] imageRatings = ratings[position];


        holder.image1.setImageResource(images[0]);
        holder.image2.setImageResource(images[1]);
        holder.image3.setImageResource(images[2]);
        holder.image4.setImageResource(images[3]);
        holder.image5.setImageResource(images[4]);

        holder.ratingBar1.setRating(imageRatings[0]);
        holder.ratingBar2.setRating(imageRatings[1]);
        holder.ratingBar3.setRating(imageRatings[2]);
        holder.ratingBar4.setRating(imageRatings[3]);
        holder.ratingBar5.setRating(imageRatings[4]);

        holder.ratingBar1.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                imageRatings[0] = rating;
            }
        });


        holder.ratingBar2.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                imageRatings[1] = rating;
            }
        });


        holder.ratingBar3.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                imageRatings[2] = rating;
            }
        });


        holder.ratingBar4.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                imageRatings[3] = rating;
            }
        });


        holder.ratingBar5.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                imageRatings[4] = rating;
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageSets.length;
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView image1, image2, image3, image4, image5;
        RatingBar ratingBar1, ratingBar2, ratingBar3, ratingBar4, ratingBar5;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
            image2 = itemView.findViewById(R.id.image2);
            image3 = itemView.findViewById(R.id.image3);
            image4 = itemView.findViewById(R.id.image4);
            image5 = itemView.findViewById(R.id.image5);

            ratingBar1 = itemView.findViewById(R.id.ratingBar1);
            ratingBar2 = itemView.findViewById(R.id.ratingBar2);
            ratingBar3 = itemView.findViewById(R.id.ratingBar3);
            ratingBar4 = itemView.findViewById(R.id.ratingBar4);
            ratingBar5 = itemView.findViewById(R.id.ratingBar5);

        }
    }


}