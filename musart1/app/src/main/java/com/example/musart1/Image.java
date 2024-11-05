package com.example.musart1;

import android.graphics.Bitmap;

public class Image {
    private int id;
    private String name;
    private String path;
    private String tag;


    // Constructor
    public Image(int id, String name, String path, String tag) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.tag = tag;

    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getTag() {
        return tag;
    }

}