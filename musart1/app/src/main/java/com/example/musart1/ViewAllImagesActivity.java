package com.example.musart1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;


import java.util.ArrayList;
import java.util.List;

public class ViewAllImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_images);

        recyclerView = findViewById(R.id.recycler_view_all_images);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3); // 3 columnas
        recyclerView.setLayoutManager(gridLayoutManager);


        List<ImageData> allImages = loadImagesFromDatabase();


        if (allImages == null || allImages.isEmpty()) {
            Toast.makeText(this, "No hay imágenes para mostrar", Toast.LENGTH_SHORT).show();
            return;
        }

        galleryAdapter = new GalleryAdapter(allImages, (position, rating) -> {
            Toast.makeText(this, "Visualizando imagen: " + allImages.get(position).getImagePath(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(galleryAdapter);
    }


    private List<ImageData> loadImagesFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        return dbHelper.getAllImages();
    }

    public void onBackButtonClick(View view) {

        Intent intent = new Intent(ViewAllImagesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}


