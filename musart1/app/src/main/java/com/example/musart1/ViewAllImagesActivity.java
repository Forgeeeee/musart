package com.example.musart1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAllImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;
    private List<ImageData> allImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_images);

        recyclerView = findViewById(R.id.recycler_view_all_images);

        // Configurar el diseño en cuadrícula
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3); // 3 columnas
        recyclerView.setLayoutManager(gridLayoutManager);

        // Inicializar lista y cargar imágenes desde Firebase
        allImages = new ArrayList<>();
        loadImagesFromFirebase();
    }

    private void loadImagesFromFirebase() {
        // Referencia al nodo "images" en Firebase
        FirebaseDatabase.getInstance().getReference("images")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        allImages.clear(); // Limpia la lista antes de agregar nuevos datos
                        for (DataSnapshot data : snapshot.getChildren()) {
                            ImageData imageData = data.getValue(ImageData.class);
                            if (imageData != null) {
                                allImages.add(imageData);
                            }
                        }

                        // Actualizar adaptador con las imágenes cargadas
                        if (galleryAdapter == null) {
                            galleryAdapter = new GalleryAdapter(allImages, (position, rating) -> {
                                Toast.makeText(ViewAllImagesActivity.this,
                                        "Visualizando imagen: " + allImages.get(position).getImagePath(),
                                        Toast.LENGTH_SHORT).show();
                            });
                            recyclerView.setAdapter(galleryAdapter);
                        } else {
                            galleryAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("ViewAllImagesActivity", "Error al cargar las imágenes", error.toException());
                    }
                });
    }

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(ViewAllImagesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
