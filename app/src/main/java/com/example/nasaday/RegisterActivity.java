package com.example.nasaday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView text2 = findViewById(R.id.textView2);
        EditText typeField = findViewById(R.id.editText3);

        Button registerButton = findViewById(R.id.button2);

        registerButton.setOnClickListener(click -> {
            Snackbar.make(typeField, getResources().getString(R.string.snackbar_message), Snackbar.LENGTH_LONG).show();
        });
        text2.setOnClickListener(click -> {
            finish();
        });
    }
}
