package com.example.firstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    /**
     * On upgrade
     *
     * @param db SQLiteDatabase
     * @param oldVersion int
     * @param newVersion int
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    /**
     * Create users table
     *
     * @param db SQLiteDatabase
     */
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

    /**
     * Create countries table
     *
     * @param db SQLiteDatabase
     */
    private void createCountriesTable(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS countries;");
        db.execSQL("CREATE TABLE countries (" +
                "country_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "square INTEGER," +
                "capital TEXT," +
                "image_id INTEGER," +
                "currency TEXT);"
        );
    }

    /**
     * Create saved countries table
     *
     * @param db SQLiteDatabase
     */
    private void createSavedCountriesTable(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS saved_countries;");
        db.execSQL("CREATE TABLE saved_countries (" +
                "entity_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "country_id INTEGER NOT NULL);"
        );
    }

    /**
     * Insert initial data
     *
     * @param db SQLiteDatabase
     */
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

    /**
     * Save country info
     *
     * @param data ContentValues
     * @return long
     */
    public long saveCountry(ContentValues data)
    {
        if (data.getAsInteger("id") == null) {
            return this.getWritableDatabase().insert("countries", null, data);
        }
        return this.getWritableDatabase().update(
                "countries",
                data,
                "country_id = ?",
                new String [] { String.valueOf(data.getAsInteger("id")) }
        );
    }

    public ArrayList<ContentValues> selectCountries()
    {
        ArrayList<ContentValues> result = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().query("countries",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                ContentValues cv = new ContentValues();
                cv.put("id", cursor.getInt(cursor.getColumnIndex("country_id")));
                cv.put("name", cursor.getString(cursor.getColumnIndex("name")));
                cv.put("square", cursor.getString(cursor.getColumnIndex("square")));
                cv.put("capital", cursor.getString(cursor.getColumnIndex("capital")));
                result.add(cv);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }
}
