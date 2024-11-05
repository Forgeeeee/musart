package com.example.musart1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        Cursor cursor = db.rawQuery("SELECT * FROM images", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String path = cursor.getString(2);
                String tag = cursor.getString(3);
                Image image = new Image(id, name, path, tag);
                imageList.add(image);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return imageList;
    }

    // Método para actualizar una imagen
    public void updateImage(Image image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", image.getName());
        values.put("path", image.getPath());
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
        Cursor cursor = db.rawQuery("SELECT * FROM images WHERE tag = ?", new String[]{tag});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String path = cursor.getString(2);
                Image image = new Image(id, name, path, tag);
                imageList.add(image);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return imageList;
    }



}