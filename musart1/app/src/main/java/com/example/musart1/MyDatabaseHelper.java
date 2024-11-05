package com.example.musart1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    // Nombre de la base de datos y versión
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE images (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, path TEXT, tag TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si hay una actualización de la base de datos
        db.execSQL("DROP TABLE IF EXISTS images");
        onCreate(db);
    }
}