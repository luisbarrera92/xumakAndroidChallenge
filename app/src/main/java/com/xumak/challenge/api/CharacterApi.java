package com.xumak.challenge.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xumak.challenge.R;
import com.xumak.challenge.models.Character;
import com.xumak.challenge.utils.WebServiceNetwork;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterApi {
    private final WebServiceNetwork webServiceNetwork;
    private final String API_URL;

    public CharacterApi(@NotNull Context context) {
        this.webServiceNetwork = new WebServiceNetwork();
        this.API_URL = context.getString(R.string.API_URL);
    }

    ///api/characters
    public List<Character> getAll() {
        List<Character> characters = new ArrayList<>();
        try {
            String result = this.webServiceNetwork.GETData(this.API_URL + "/api/characters");
            Gson gson = new GsonBuilder().create();
            characters.addAll(Arrays.asList(gson.fromJson(result, Character[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return characters;
    }
}
