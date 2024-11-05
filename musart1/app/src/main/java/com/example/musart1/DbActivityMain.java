package com.example.musart1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.graphics.Bitmap;


public class DbActivityMain extends AppCompatActivity {


    private DatabaseManager dbManager;
    private EditText etImageName, etImagePath, etImageTag;
    private TextView tvResults;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedImagePath = null;
    private LinearLayout linearLayoutResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_view);

        dbManager = new DatabaseManager(getApplicationContext());

        etImageName = findViewById(R.id.etImageName);
        etImageTag = findViewById(R.id.etImageTag);

        tvResults = findViewById(R.id.tvResults);


        Button btnInsertImage = findViewById(R.id.btnInsertImage);
        btnInsertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etImageName.getText().toString();
                String tag = etImageTag.getText().toString();

                if (!name.isEmpty() && !tag.isEmpty()) {
                    openImageSelector();
                } else {
                    tvResults.setText("Por favor, ingresa nombre y etiqueta");
                }
            }
        });

        Button btnGetAllImages = findViewById(R.id.btnGetAllImages);
        btnGetAllImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Image> images = dbManager.getAllImages();
                LinearLayout imageContainer = findViewById(R.id.imageContainer);
                imageContainer.removeAllViews();

                if (images.isEmpty()) {

                    ImageView placeholderImageView = new ImageView(DbActivityMain.this);
                    placeholderImageView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            300
                    ));
                    placeholderImageView.setImageResource(R.drawable.placeholder);


                    TextView noImagesText = new TextView(DbActivityMain.this);
                    noImagesText.setText("No hay imágenes disponibles.");


                    imageContainer.addView(placeholderImageView);
                    imageContainer.addView(noImagesText);
                } else {

                    for (Image image : images) {
                        ImageView imageView = new ImageView(DbActivityMain.this);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                300
                        ));
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setImageURI(Uri.parse(image.getPath()));


                        TextView textView = new TextView(DbActivityMain.this);
                        textView.setText("Nombre: " + image.getName() + "\nEtiqueta: " + image.getTag());

                        imageContainer.addView(imageView);

                    }
                }
            }
        });


                Button btnUpdateImage = findViewById(R.id.btnUpdateImage);
        btnUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = etImageTag.getText().toString();
                String name = etImageName.getText().toString();


                if (!tag.isEmpty() && !name.isEmpty() ) {
                    ArrayList<Image> images = dbManager.getImagesByTag(tag);
                    if (!images.isEmpty()) {
                        Image imageToUpdate = images.get(0);
                        dbManager.updateImage(imageToUpdate);
                        tvResults.setText("Imagen actualizada: Nombre " + name );
                    } else {
                        tvResults.setText("No se encontró ninguna imagen con la etiqueta: " + tag);
                    }
                } else {
                    tvResults.setText("Por favor, ingresa etiqueta y nombre");
                }
            }
        });

        Button btnDeleteImage = findViewById(R.id.btnDeleteImage);
        btnDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = etImageTag.getText().toString();
                if (!tag.isEmpty()) {
                    ArrayList<Image> images = dbManager.getImagesByTag(tag);
                    if (!images.isEmpty()) {
                        dbManager.deleteImage(images.get(0).getId());
                        tvResults.setText("Imagen eliminada con etiqueta: " + tag);
                    } else {
                        tvResults.setText("No se encontró ninguna imagen con la etiqueta: " + tag);
                    }
                } else {
                    tvResults.setText("Por favor, ingresa una etiqueta");
                }
            }
        });

        linearLayoutResults = findViewById(R.id.imageContainer);
        Button btnSearchByTag = findViewById(R.id.btnSearchByTag);

        btnSearchByTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = etImageTag.getText().toString();
                linearLayoutResults.removeAllViews();

                if (!tag.isEmpty()) {
                    ArrayList<Image> images = dbManager.getImagesByTag(tag);

                    if (images.isEmpty()) {
                        TextView noImagesText = new TextView(DbActivityMain.this);
                        noImagesText.setText("No se encontraron imágenes con la etiqueta " + tag);
                        linearLayoutResults.addView(noImagesText);
                    } else {
                        for (Image image : images) {
                            ImageView imageView = new ImageView(DbActivityMain.this);
                            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            imageView.setAdjustViewBounds(true);
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


                            imageView.setImageURI(Uri.parse(image.getPath()));

                            linearLayoutResults.addView(imageView);
                        }
                    }
                } else {
                    TextView errorText = new TextView(DbActivityMain.this);
                    errorText.setText("Por favor, ingresa una etiqueta");
                    linearLayoutResults.addView(errorText);
                }
            }
        });
    }

    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            selectedImagePath = imageUri.toString();


            String name = etImageName.getText().toString();
            String tag = etImageTag.getText().toString();
            Image newImage = new Image(0, name, selectedImagePath, tag);
            dbManager.insertImage(newImage);


            LinearLayout imageContainer = findViewById(R.id.imageContainer);


            ImageView imageView = new ImageView(DbActivityMain.this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    300
            ));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageURI(imageUri);


            imageContainer.addView(imageView);


            tvResults.setText("Imagen insertada: " + name + ", Etiqueta: " + tag);
        }
    } }