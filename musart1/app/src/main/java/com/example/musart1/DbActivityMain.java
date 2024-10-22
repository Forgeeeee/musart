package com.example.musart1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DbActivityMain extends AppCompatActivity {

    private DatabaseManager dbManager;
    private EditText etStudentName, etStudentYear, etStudentId;
    private TextView tvResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_view);

        DatabaseManager dbManager = new DatabaseManager(getApplicationContext());


        etStudentName = findViewById(R.id.etStudentName);
        etStudentYear = findViewById(R.id.etStudentYear);
        etStudentId = findViewById(R.id.etStudentId);
        tvResults = findViewById(R.id.tvResults);


        Button btnInsertStudent = findViewById(R.id.btnInsertStudent);
        btnInsertStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etStudentName.getText().toString();
                String yearString = etStudentYear.getText().toString();

                if (!name.isEmpty() && !yearString.isEmpty()) {
                    int year = Integer.parseInt(yearString);
                    Student newStudent = new Student(name, year);
                    dbManager.insertStudent(newStudent);
                    tvResults.setText("Estudiante insertado: " + name + ", Año: " + year);
                } else {
                    tvResults.setText("Por favor, ingresa nombre y año");
                }
            }
        });


        Button btnGetAllStudents = findViewById(R.id.btnGetAllStudents);
        btnGetAllStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Student> students = dbManager.getAllStudents();
                StringBuilder result = new StringBuilder("Estudiantes:\n");
                for (Student student : students) {
                    result.append("ID: ").append(student.getId())
                            .append(", Nombre: ").append(student.getName())
                            .append(", Año: ").append(student.getYear()).append("\n");
                }
                tvResults.setText(result.toString());
            }
        });


        Button btnUpdateStudent = findViewById(R.id.btnUpdateStudent);
        btnUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idString = etStudentId.getText().toString();
                String name = etStudentName.getText().toString();
                String yearString = etStudentYear.getText().toString();

                if (!idString.isEmpty() && !name.isEmpty() && !yearString.isEmpty()) {
                    int id = Integer.parseInt(idString);
                    int year = Integer.parseInt(yearString);
                    Student updatedStudent = new Student(id, name, year);
                    dbManager.updateStudent(updatedStudent);
                    tvResults.setText("Estudiante actualizado: ID " + id + ", Nombre: " + name + ", Año: " + year);
                } else {
                    tvResults.setText("Por favor, ingresa ID, nombre y año");
                }
            }
        });


        Button btnDeleteStudent = findViewById(R.id.btnDeleteStudent);
        btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idString = etStudentId.getText().toString();
                if (!idString.isEmpty()) {
                    int id = Integer.parseInt(idString);
                    dbManager.deleteStudent(id);
                    tvResults.setText("Estudiante eliminado con ID: " + id);
                } else {
                    tvResults.setText("Por favor, ingresa ID");
                }
            }
        });







// Insertar un nuevo estudiante
        Student newStudent = new Student("Juan", 2);  // Juan está en su segundo año
        dbManager.insertStudent(newStudent);

// Obtener todos los estudiantes
        ArrayList<Student> students = dbManager.getAllStudents();
        for (Student student : students) {
            System.out.println(student.toString());
        }

// Actualizar un estudiante
        Student updatedStudent = new Student(1, "Juan Pérez", 3);  // Actualizamos a tercer año
        dbManager.updateStudent(updatedStudent);

// Eliminar un estudiante
        dbManager.deleteStudent(1);  // Suponiendo que el ID es 1

// Obtener un estudiante por su ID
        Student studentById = dbManager.getStudentById(1);
        if (studentById != null) {
            System.out.println("Estudiante encontrado: " + studentById.getName());
        } else {
            System.out.println("Estudiante no encontrado");
        }

// Obtener todos los estudiantes por su year (año)
        ArrayList<Student> studentsByYear = dbManager.getStudentsByYear(2);
        for (Student student : studentsByYear) {
            System.out.println("Estudiante: " + student.getName() + ", Año: " + student.getYear());
        }
    }




}
