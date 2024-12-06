package com.example.musart1;


import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private final List<ImageData> imageList;
    private final OnImageClickListener onImageClickListener;

    public interface OnImageClickListener {
        void onImageClick(int position, float rating);
    }

    public GalleryAdapter(List<ImageData> imageList, OnImageClickListener onImageClickListener) {
        this.imageList = imageList;
        this.onImageClickListener = onImageClickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageData imageData = imageList.get(position);

        if (imageData.getImagePath() != null) {

            Uri imageUri = Uri.parse(imageData.getImagePath());

            Glide.with(holder.itemView.getContext())
                    .load(imageUri)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .centerCrop())
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.placeholder);
        }

        holder.ratingBar.setRating(imageData.getRating());

        if (imageData.isSelected()) {
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }


        holder.itemView.setOnLongClickListener(v -> {
            boolean isSelected = !imageData.isSelected();
            imageData.setSelected(isSelected);
            notifyItemChanged(position);
            return true;
        });

        holder.itemView.setOnLongClickListener(v -> {
            boolean isSelected = !imageData.isSelected();
            imageData.setSelected(isSelected);
            notifyItemChanged(position);

            return true;
        });

        holder.itemView.setOnClickListener(v -> {

            boolean isSelected = !imageData.isSelected();
            imageData.setSelected(isSelected);
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }
    }
}
