package com.example.musart1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DbActivityMain extends AppCompatActivity {


    private DatabaseManager dbManager;
    private EditText etImageName, etImagePath, etImageTag;
    private TextView tvResults;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_view);

        dbManager = new DatabaseManager(getApplicationContext());

        etImageName = findViewById(R.id.etImageName);
        etImageTag = findViewById(R.id.etImageTag);
        etImagePath = findViewById(R.id.etImagePath); // Asegúrate de que este EditText esté en el layout db_view.xml
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
                StringBuilder result = new StringBuilder("Imágenes:\n");
                for (Image image : images) {
                    result.append("ID: ").append(image.getId())
                            .append(", Nombre: ").append(image.getName())
                            .append(", Ruta: ").append(image.getPath())
                            .append(", Etiqueta: ").append(image.getTag()).append("\n");
                }
                tvResults.setText(result.toString());
            }
        });

        Button btnUpdateImage = findViewById(R.id.btnUpdateImage);
        btnUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = etImageTag.getText().toString();  // Usando la etiqueta para encontrar la imagen
                String name = etImageName.getText().toString();
                String path = etImagePath.getText().toString();

                if (!tag.isEmpty() && !name.isEmpty() && !path.isEmpty()) {
                    ArrayList<Image> images = dbManager.getImagesByTag(tag);
                    if (!images.isEmpty()) {
                        Image imageToUpdate = images.get(0);  // Asumiendo que solo actualizamos la primera imagen encontrada
                        imageToUpdate.setName(name);
                        imageToUpdate.setPath(path);
                        dbManager.updateImage(imageToUpdate);
                        tvResults.setText("Imagen actualizada: Nombre " + name + ", Ruta: " + path);
                    } else {
                        tvResults.setText("No se encontró ninguna imagen con la etiqueta: " + tag);
                    }
                } else {
                    tvResults.setText("Por favor, ingresa etiqueta, nombre y ruta");
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
                        dbManager.deleteImage(images.get(0).getId());  // Asumiendo que solo eliminamos la primera imagen encontrada
                        tvResults.setText("Imagen eliminada con etiqueta: " + tag);
                    } else {
                        tvResults.setText("No se encontró ninguna imagen con la etiqueta: " + tag);
                    }
                } else {
                    tvResults.setText("Por favor, ingresa una etiqueta");
                }
            }
        });

        Button btnSearchByTag = findViewById(R.id.btnSearchByTag);
        btnSearchByTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = etImageTag.getText().toString();
                if (!tag.isEmpty()) {
                    ArrayList<Image> images = dbManager.getImagesByTag(tag);
                    StringBuilder result = new StringBuilder("Imágenes con etiqueta " + tag + ":\n");
                    for (Image image : images) {
                        result.append("ID: ").append(image.getId())
                                .append(", Nombre: ").append(image.getName())
                                .append(", Ruta: ").append(image.getPath()).append("\n");
                    }
                    tvResults.setText(result.toString());
                } else {
                    tvResults.setText("Por favor, ingresa una etiqueta");
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

            // Inserta la imagen en la base de datos con el path seleccionado
            String name = etImageName.getText().toString();
            String tag = etImageTag.getText().toString();
            Image newImage = new Image(0, name, selectedImagePath, tag);
            dbManager.insertImage(newImage);

            tvResults.setText("Imagen insertada: " + name + ", Etiqueta: " + tag + ", Ruta: " + selectedImagePath);
        }
    }
}