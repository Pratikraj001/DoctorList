package com.example.trrevpostman;

public class DataModal {
    private String id, name, email,gender, month, year;



    public DataModal(String id, String name, String email, String gender, String month, String year) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.month = month;
        this.year = year;
    }

    public DataModal() {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String m) {
        this.gender = gender;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
