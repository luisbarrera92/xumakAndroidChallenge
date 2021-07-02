package com.xumak.challenge.actions;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xumak.challenge.interfaces.DBActions;
import com.xumak.challenge.models.Character;
import com.xumak.challenge.utils.DBHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CharacterActions implements DBActions<Character> {
    private final DBHelper conn;

    public CharacterActions(Context ctx) {
        this.conn = new DBHelper(ctx);
    }

    //ADD A NEW CHARACTER
    @Override
    public void add(Character item) {
        add(Collections.singletonList(item));
    }

    //ADD A COLLECTION OF CHARACTERS
    @Override
    public void add(@NotNull Iterable<Character> items) {
        final SQLiteDatabase db = conn.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Character item : items) {
                ContentValues values = new ContentValues();
                values.put("ID", item.getChar_id());
                values.put("NAME", item.getName());
                values.put("STATUS", item.getStatus());
                db.insert("CHARACTER", null, values);
                //ADD OCCUPATIONS OF NEW CHARACTER
                for (String occupation : item.getOccupation()) {
                    values.clear();
                    values.put("ID_CHARACTER", item.getChar_id());
                    values.put("OCCUPATION", occupation);
                    db.insert("OCCUPATION", null, values);
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    //UPDATES A CHARACTER
    @Override
    public void update(@NotNull Character item) {
        try (SQLiteDatabase db = conn.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("NAME", item.getName());
            values.put("STATUS", item.getStatus());
            values.put("FAVORITE", item.getFavorite());
            String selection = "ID = ?";
            String[] selectionArgs = {String.valueOf(item.getChar_id())};
            db.update("CHARACTER", values, selection, selectionArgs);
        }
    }

    //QUERIES A LIST OF CHARACTERS
    @Override
    public List<Character> query(Map<String, String> parameters) {
        List<Character> characters = new ArrayList<>();
        try (SQLiteDatabase db = conn.getReadableDatabase()) {
            StringBuilder sqlQuery;
            sqlQuery = new StringBuilder("SELECT * FROM CHARACTER");
            if (!parameters.isEmpty()) {
                sqlQuery.append(" WHERE");
                int i = 0;
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    if (i > 0) sqlQuery.append(" AND");
                    sqlQuery.append(" ").append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
                    i++;
                }
            }
            sqlQuery.append(" ORDER BY FAVORITE DESC, ID ASC");
            Cursor cursor = db.rawQuery(sqlQuery.toString(), null);
            Character character;
            while (cursor.moveToNext()) {
                character = new Character();
                character.setChar_id(cursor.getInt(0));//ID
                character.setName(cursor.getString(1));//NAME
                character.setStatus(cursor.getString(2));//STATUS
                character.setFavorite(cursor.getInt(3));//FAV
                character.setOccupation(new ArrayList<>());
                //GET OCCUPATIONS OF CHARACTER
                sqlQuery = new StringBuilder("SELECT * FROM OCCUPATION WHERE ID_CHARACTER = '" + character.getChar_id() + "'");
                Cursor cursor1 = db.rawQuery(sqlQuery.toString(), null);
                while (cursor1.moveToNext()) {
                    character.getOccupation().add(cursor1.getString(1));//OCCUPATION
                }
                cursor1.close();
                characters.add(character);
            }
            cursor.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return characters;
    }
}
