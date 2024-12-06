package com.example.musart1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GalleryAdapter.OnImageClickListener {

    private ArrayList<ImageData> imageList;
    private GalleryAdapter galleryAdapter;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        galleryAdapter = new GalleryAdapter(imageList, this);
        recyclerView.setAdapter(galleryAdapter);

        Button buttonAddImage = findViewById(R.id.button_add_image);
        buttonAddImage.setOnClickListener(v -> openImagePicker());

        Button buttonDeleteImages = findViewById(R.id.button_delete_images);
        buttonDeleteImages.setOnClickListener(v -> deleteSelectedImages());

        Button buttonViewAllImages = findViewById(R.id.button_view_all_images);
        buttonViewAllImages.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewAllImagesActivity.class);
            startActivity(intent);
        });


        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            addImage(selectedImageUri.toString());
                        }
                    }
                }
        );


        loadImagesFromFirebase();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private void addImage(String imagePath) {
        Log.d("MainActivity", "Imagen seleccionada: " + imagePath);


        ImageData newImage = new ImageData(imagePath, 0.0f);
        imageList.add(newImage);
        galleryAdapter.notifyItemInserted(imageList.size() - 1);


        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.insertImage(imagePath, 0.0f);

        Toast.makeText(this, "Imagen agregada", Toast.LENGTH_SHORT).show();
    }

    private void loadImagesFromFirebase() {
        FirebaseDatabase.getInstance().getReference("images")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        imageList.clear(); // Limpia la lista antes de agregar nuevos datos
                        for (DataSnapshot data : snapshot.getChildren()) {
                            ImageData imageData = data.getValue(ImageData.class);
                            if (imageData != null) {
                                imageList.add(imageData);
                            }
                        }
                        galleryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("MainActivity", "Error al cargar las imágenes", error.toException());
                    }
                });
    }

    private void deleteSelectedImages() {
        FirebaseHelper firebaseHelper = new FirebaseHelper();

        ArrayList<ImageData> toRemove = new ArrayList<>();
        for (ImageData image : imageList) {
            if (image.isSelected()) {

                firebaseHelper.getAllImages()
                        .orderByChild("imagePath")
                        .equalTo(image.getImagePath())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    data.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("MainActivity", "Error al eliminar imagen", error.toException());
                            }
                        });


                toRemove.add(image);
            }
        }
        imageList.removeAll(toRemove);
        galleryAdapter.notifyDataSetChanged();

        Toast.makeText(this, toRemove.size() + " imagen eliminada", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImageClick(int position, float rating) {
        ImageData image = imageList.get(position);
        image.setRating(rating);
        Toast.makeText(this, "Calificación actualizada: " + rating, Toast.LENGTH_SHORT).show();
    }
}
