package com.example.musart1;

public class ImageData {
    private String imagePath;
    private float rating;
    private boolean isSelected;

    public ImageData(String imagePath, float rating) {
        this.imagePath = imagePath;
        this.rating = rating;
        this.isSelected = false;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}