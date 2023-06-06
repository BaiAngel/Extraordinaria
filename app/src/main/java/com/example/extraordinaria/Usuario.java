package com.example.extraordinaria;

public class Usuario {
    private String name;
    private String password;
    private String phone;
    private String gender;
    private String country;
    private int day;
    private int month;
    private int year;

    public Usuario() {
        // Constructor vacío requerido para Firestore
    }

    public Usuario(String name, String password, String phone, String gender, String country, int day, int month, int year) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.country = country;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
