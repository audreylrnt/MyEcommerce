package com.example.myecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    EditText etUsername, etPassword;
    Database databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        databaseHelper = new Database(this);
        etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        getUser();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your credential", Toast.LENGTH_SHORT).show();
                } else {

                    if (etUsername.getText().toString().equals("Smit") && etPassword.getText().toString().equals("_sm1t_OK")) {
                        Intent productList = new Intent(MainActivity.this, ProductListActivity.class);
                        startActivity(productList);
                    } else {
                        Toast.makeText(MainActivity.this, "Wrong credential", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void getUser() {
        Cursor c = databaseHelper.getUserData();
        UserInit.userInit.clear();
        while (c.moveToNext()) {
            @SuppressLint("Range") String name = c.getString(c.getColumnIndex(Database.TABLE_USER_NAME));
            @SuppressLint("Range") String password = c.getString(c.getColumnIndex(Database.TABLE_USER_TABLE_PASSWORD));
            User user = new User(name, password);
            Log.d("MainActivity", "getUser: name" + name);
            UserInit.userInit.add(user);
        }
    }
}