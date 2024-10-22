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

    // Método para insertar un estudiante
    public void insertStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("year", student.getYear());
        db.insert("students", null, values);
        db.close();
    }

    // Método para obtener todos los estudiantes
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM students", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int year = cursor.getInt(2);
                Student student = new Student(id, name, year);
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    // Método para actualizar un estudiante
    public void updateStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("year", student.getYear());
        db.update("students", values, "id = ?", new String[]{String.valueOf(student.getId())});
        db.close();
    }

    // Método para eliminar un estudiante
    public void deleteStudent(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("students", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Student getStudentById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM students WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(1);
            int year = cursor.getInt(2);
            Student student = new Student(id, name, year);
            cursor.close();
            db.close();
            return student;
        }
        // Si no se encuentra un estudiante con ese id, devuelve null
        cursor.close();
        db.close();
        return null;
    }

    public ArrayList<Student> getStudentsByYear(int year) {
        ArrayList<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM students WHERE year = ?", new String[]{String.valueOf(year)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                Student student = new Student(id, name, year);
                studentList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;
    }


}
