package com.example.musart1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "musart.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_IMAGES = "images";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_RATING = "rating";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_IMAGES_TABLE = "CREATE TABLE " + TABLE_IMAGES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_IMAGE + " TEXT, "
                + COLUMN_RATING + " REAL)";
        db.execSQL(CREATE_IMAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(db);
    }


    public void insertImage(String image, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_RATING, rating);

        db.insert(TABLE_IMAGES, null, values);
        db.close();
    }



    public List<ImageData> getAllImages() {
        List<ImageData> images = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(
                    TABLE_IMAGES,
                    new String[]{COLUMN_ID, COLUMN_IMAGE, COLUMN_RATING},
                    null, null, null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                    float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING));

                    Log.d("DatabaseHelper", "Imagen  de la base de datos: " + image);

                    images.add(new ImageData(image, rating));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return images;
    }
}
