package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity
{
    EditText login;
    EditText pass;
    Button button;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //this.dbHelper = new DBHelper(this, "bd", null, 1);
        this.login = (EditText) findViewById(R.id.login);
        this.pass = (EditText) findViewById(R.id.pass);
        this.button = (Button) findViewById(R.id.submit_auth);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginText = login.getText().toString();
                String passText = pass.getText().toString();
                if (!passText.equals(getString(R.string.password))) {
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("login", loginText);
                startActivity(intent);
            }
        };

        button.setOnClickListener(onClickListener);
    }
}
