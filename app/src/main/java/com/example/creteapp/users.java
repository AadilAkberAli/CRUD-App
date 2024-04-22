package com.example.creteapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class users {
    @PrimaryKey
    private int id;
    private String name;
    private String email;

    public users(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }


    public int getId() {
        return id;
    }

    public String getUsername() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
