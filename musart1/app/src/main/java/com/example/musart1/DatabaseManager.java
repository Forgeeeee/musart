package com.example.musart1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class DatabaseManager {

    private MyDatabaseHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }




    // Método para insertar una nueva imagen
    public void insertImage(Image image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", image.getName());
        values.put("path", image.getPath());
        values.put("tag", image.getTag());
        db.insert("images", null, values);
        db.close();
    }

    // Método para obtener todas las imágenes
    public ArrayList<Image> getAllImages() {
        ArrayList<Image> imageList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query("images", null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                // Obtener los índices de las columnas
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("name");
                int pathIndex = cursor.getColumnIndex("path");
                int tagIndex = cursor.getColumnIndex("tag");

                // Verificar que todos los índices sean válidos
                if (idIndex >= 0 && nameIndex >= 0 && pathIndex >= 0 && tagIndex >= 0) {
                    do {
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String path = cursor.getString(2);
                        String tag = cursor.getString(3);

                        Image image = new Image(id, name, path, tag);
                        imageList.add(image);
                    } while (cursor.moveToNext());
                } else {
                    Log.e("DatabaseError", "Column index not found. Check the table schema.");
                }
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error fetching all images", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return imageList;
    }

    // Método para actualizar una imagen
    public void updateImage(Image image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", image.getName());
        values.put("tag", image.getTag());
        db.update("images", values, "id = ?", new String[]{String.valueOf(image.getId())});
        db.close();
    }

    // Método para eliminar una imagen por ID
    public void deleteImage(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("images", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Método para obtener imágenes por etiqueta
    public ArrayList<Image> getImagesByTag(String tag) {
        ArrayList<Image> imageList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Usamos un rawQuery para obtener imágenes por etiqueta
            cursor = db.rawQuery("SELECT * FROM images WHERE tag = ?", new String[]{tag});

            // Verificamos que el cursor no sea nulo y que tenga resultados
            if (cursor != null && cursor.moveToFirst()) {
                // Obtener los índices de las columnas
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("name");
                int pathIndex = cursor.getColumnIndex("path");
                int tagIndex = cursor.getColumnIndex("tag");

                // Verificamos que todos los índices sean válidos
                if (idIndex >= 0 && nameIndex >= 0 && pathIndex >= 0 && tagIndex >= 0) {
                    do {
                        int id = cursor.getInt(idIndex);
                        String name = cursor.getString(nameIndex);
                        String path = cursor.getString(pathIndex);
                        String tagFromDb = cursor.getString(tagIndex); // Cambiamos el nombre de la variable para evitar confusión

                        Image image = new Image(id, name, path, tagFromDb);
                        imageList.add(image);
                    } while (cursor.moveToNext());
                } else {
                    Log.e("DatabaseError", "Column index not found. Check the table schema.");
                }
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error fetching images by tag", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return imageList;
    }



}