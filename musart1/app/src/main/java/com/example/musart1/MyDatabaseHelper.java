package com.example.musart1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "databaseimages.db"; //
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de imágenes
        String createImagesTable = "CREATE TABLE IF NOT EXISTS images (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "path TEXT, " +
                "tag TEXT)";
        db.execSQL(createImagesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejo de versiones de base de datos (puedes agregar lógica aquí si es necesario)
        db.execSQL("DROP TABLE IF EXISTS images");
        onCreate(db); // Vuelve a crear la tabla
    }
}