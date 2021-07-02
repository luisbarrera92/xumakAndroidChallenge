package com.xumak.challenge.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DBVER = 1;
    private static final String DBNAME = "dbXumakChallenge.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATES CHARACTER TABLE
        String strSql = "CREATE TABLE CHARACTER ("
                + " ID INTEGER PRIMARY KEY,"
                + " NAME TEXT,"
                + " STATUS TEXT,"
                + " FAVORITE INTEGER DEFAULT 0)";

        db.execSQL(strSql);
        Log.e("Debug", "CHARACTER TABLE CREATED: " + strSql);

        //CREATES CHARECTER OCCUPATIONS TABLE
        strSql = "CREATE TABLE OCCUPATION ("
                + " ID_CHARACTER INTEGER,"
                + " OCCUPATION TEXT)";

        db.execSQL(strSql);
        Log.e("Debug", "CHARACTER OCCUPATION TABLE CREATED: " + strSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
