package com.example.musart1;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    private final DatabaseReference databaseReference;

    public FirebaseHelper() {

        databaseReference = FirebaseDatabase.getInstance().getReference("images");
    }


    public void insertImage(String imagePath, float rating) {
        String id = databaseReference.push().getKey(); // Genera una ID única
        if (id != null) {
            ImageData imageData = new ImageData(imagePath, rating);
            databaseReference.child(id).setValue(imageData)
                    .addOnSuccessListener(aVoid -> Log.d("FirebaseHelper", "Imagen guardada"))
                    .addOnFailureListener(e -> Log.e("FirebaseHelper", "Error al guardar la imagen", e));
        }
    }


    public DatabaseReference getAllImages() {
        return databaseReference;
    }


    public void deleteImage(String id) {
        databaseReference.child(id).removeValue()
                .addOnSuccessListener(aVoid -> Log.d("FirebaseHelper", "Imagen eliminada"))
                .addOnFailureListener(e -> Log.e("FirebaseHelper", "Error al eliminar la imagen", e));
    }
}