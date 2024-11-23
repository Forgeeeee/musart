package com.example.musart1;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
    }


    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        } else {
            return null;
        }
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

        saveImageToDatabase(imagePath);
        Toast.makeText(this, "Imagen agregada", Toast.LENGTH_SHORT).show();
    }


    private void saveImageToDatabase(String imagePath) {
        Log.d("MainActivity", "Guardando imagen en base de datos: " + imagePath);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.insertImage(imagePath, 0.0f);
        Toast.makeText(this, "Imagen guardada en la base de datos", Toast.LENGTH_SHORT).show();
    }


    private void deleteSelectedImages() {
        ArrayList<ImageData> toRemove = new ArrayList<>();
        for (ImageData image : imageList) {
            if (image.isSelected()) {
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

    private List<ImageData> getImageList() {
        return new ArrayList<>();
    }
}
