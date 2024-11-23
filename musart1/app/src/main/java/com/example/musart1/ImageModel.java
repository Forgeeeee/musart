package com.example.musart1;

public class ImageModel {
    private String url;
    private float rating;
    private boolean isSelected;

    public ImageModel(String url, float rating) {
        this.url = url;
        this.rating = rating;
        this.isSelected = false;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    private String imagePath;

    public ImageModel(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}