package com.example.firstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper
{
    private long adminUserId;

    public DBHelper(@androidx.annotation.Nullable Context context,
                    @androidx.annotation.Nullable String name,
                    @androidx.annotation.Nullable SQLiteDatabase.CursorFactory factory,
                    int version
    ) {
        super(context, name, factory, version);
    }

    public DBHelper(@androidx.annotation.Nullable Context context,
                    @androidx.annotation.Nullable String name,
                    @androidx.annotation.Nullable SQLiteDatabase.CursorFactory factory,
                    int version,
                    @androidx.annotation.Nullable DatabaseErrorHandler errorHandler
    ) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(@androidx.annotation.Nullable Context context,
                    @androidx.annotation.Nullable String name,
                    int version,
                    @androidx.annotation.NonNull SQLiteDatabase.OpenParams openParams
    ) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        this.createUsersTable(db);
        this.createCountriesTable(db);
        this.createSavedCountriesTable(db);

        this.insertPrimaryData(db);
    }

    private void createUsersTable(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS users;");
        db.execSQL("CREATE TABLE users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "login TEXT," +
                "password TEXT);"
        );
        ContentValues cv = new ContentValues();
        cv.put("login", "admin");
        cv.put("password", "0000");
        this.adminUserId = db.insert("users", null, cv);
    }

    private void createCountriesTable(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS countries;");
        db.execSQL("CREATE TABLE countries (" +
                "country_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "square TEXT," +
                "capital TEXT," +
                "image_id TEXT," +
                "currency TEXT);"
        );
    }

    private void createSavedCountriesTable(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS saved_countries;");
        db.execSQL("CREATE TABLE saved_countries (" +
                "entity_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "country_id INTEGER NOT NULL);"
        );
    }

    private void insertPrimaryData(SQLiteDatabase db)
    {
        // insert into users
        ContentValues cv = new ContentValues();
        cv.put("login", "Ivan");
        cv.put("password", "1234");
        long ivanUserId = db.insert("users", null, cv);

        cv = new ContentValues();
        cv.put("login", "Dima");
        cv.put("password", "12345");
        long dimaUserId = db.insert("users", null, cv);

        cv = new ContentValues();
        cv.put("login", "Mark");
        cv.put("password", "123456");
        long markUserId = db.insert("users", null, cv);

        cv = new ContentValues();
        cv.put("login", "Andrey");
        cv.put("password", "1234567");
        long andreyUserId = db.insert("users", null, cv);
    }

    public boolean insertRandSavedCountries(SQLiteDatabase db)
    {
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
