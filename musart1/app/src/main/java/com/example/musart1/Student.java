package com.example.musart1;

public class Student {
    private int id;
    private String name;
    private int year;

    // Constructor sin ID (para nuevos estudiantes)
    public Student(String name, int year) {
        this.name = name;
        this.year = year;
    }

    // Constructor con ID (para estudiantes ya existentes en la base de datos)
    public Student(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + name + ", Año: " + year;
    }
}
