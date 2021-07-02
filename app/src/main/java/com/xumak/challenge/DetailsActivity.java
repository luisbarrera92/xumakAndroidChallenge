package com.xumak.challenge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xumak.challenge.actions.CharacterActions;
import com.xumak.challenge.models.Character;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    private CharacterActions characterActions;
    private Map<String, String> parameters;
    private TextView txtVwDetailsName, txtVwDetailsOccupation, txtVwDetailsStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);
        Intent iParameters = getIntent();
        int id_character = iParameters.getIntExtra("ID", -1);
        characterActions = new CharacterActions(this);
        parameters = new HashMap<>();
        parameters.put("ID", Integer.toString(id_character));
        List<Character> character = characterActions.query(parameters);
        if (character.size() > 0) {
            txtVwDetailsName = findViewById(R.id.txtVwDetailsName);
            txtVwDetailsOccupation = findViewById(R.id.txtVwDetailsOccupation);
            txtVwDetailsStatus = findViewById(R.id.txtVwDetailsStatus);
            Character foundCharacter = character.get(0);
            txtVwDetailsName.setText(foundCharacter.getName());
            txtVwDetailsStatus.setText(foundCharacter.getStatus());
            StringBuilder strBuilderOccupations = new StringBuilder();
            for (String occupation : foundCharacter.getOccupation()) {
                strBuilderOccupations.append(occupation);
                strBuilderOccupations.append(System.getProperty("line.separator"));
            }
            txtVwDetailsOccupation.setText(strBuilderOccupations.toString());
        } else {
            Toast.makeText(this, "Can't find character.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}