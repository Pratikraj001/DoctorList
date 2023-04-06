package com.example.trrevpostman;

public class Articles {
    private String name;
    private String details;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Articles(String name, String details) {
        this.name = name;
        this.details = details;

    }
}
