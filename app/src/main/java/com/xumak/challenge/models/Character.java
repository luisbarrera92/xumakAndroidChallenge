package com.xumak.challenge.models;

import java.util.ArrayList;

public class Character {
    private int char_id, favorite;
    private String name, status;
    private ArrayList<String> occupation;

    public Character() {

    }

    public Character(int char_id, int favorite, String name, String status, ArrayList<String> occupation) {
        this.char_id = char_id;
        this.favorite = favorite;
        this.name = name;
        this.status = status;
        this.occupation = occupation;
    }

    public int getChar_id() {
        return char_id;
    }

    public void setChar_id(int char_id) {
        this.char_id = char_id;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(ArrayList<String> occupation) {
        this.occupation = occupation;
    }
}
