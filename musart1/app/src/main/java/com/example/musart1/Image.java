package com.example.musart1;

public class Image {
    private int id;
    private String name;
    private String path;
    private String tag;

    public Image(int id, String name, String path, String tag) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.tag = tag;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}