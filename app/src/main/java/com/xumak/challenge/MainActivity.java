package com.xumak.challenge;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.xumak.challenge.actions.CharacterActions;
import com.xumak.challenge.adapters.CharacterAdapter;
import com.xumak.challenge.api.CharacterApi;
import com.xumak.challenge.models.Character;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private CharacterActions characterActions;
    private CharacterApi characterApi;
    private Map<String, String> parameters;
    private ProgressDialog progressDialog;
    private List<Character> characters;
    private ListView lstVwCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstVwCharacters = findViewById(R.id.lstVwCharacters);
        characterActions = new CharacterActions(this);
        characterApi = new CharacterApi(this);
        parameters = new HashMap<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCharacters();
    }

    private void loadCharacters() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Characters...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Thread hilo = new Thread(() -> {
            try {
                characters = characterActions.query(parameters);
                if (characters.size() == 0) {
                    characters = characterApi.getAll();
                    characterActions.add(characters);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                CharacterAdapter characterAdapter = new CharacterAdapter(this, characters, characterActions);
                lstVwCharacters.setAdapter(characterAdapter);
                progressDialog.dismiss();
            });
        });
        hilo.start();
    }
}