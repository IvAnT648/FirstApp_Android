package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
    EditText login;
    EditText pass;
    Button button;
    DBHelper dbHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.dbHelper = new DBHelper(this, "bd", null, 1);
        this.login = (EditText) findViewById(R.id.login);
        this.pass = (EditText) findViewById(R.id.pass);
        this.button = (Button) findViewById(R.id.submit_auth);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginText = login.getText().toString();
                String passText = pass.getText().toString();
                MainActivity.userID = auth(loginText, passText);
                if (MainActivity.userID == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Неверный логин или пароль!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("login", loginText);
                startActivity(intent);
            }
        });
    }

    private int auth(String login, String password)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query(
                "users",
                new String[] { "user_id" },
                "login = ? AND password = ?",
                new String[] { login, password },
                null,
                null,
                null
        );
        if (!cursor.moveToFirst()) {
            return 0;
        }

        return cursor.getInt(cursor.getColumnIndex("user_id"));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}
