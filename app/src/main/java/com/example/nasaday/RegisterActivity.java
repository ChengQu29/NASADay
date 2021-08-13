package com.example.nasaday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

/**
 * class for the registration page
 */
public class RegisterActivity extends AppCompatActivity {

    EditText username, password;
    RegistrationHelper userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.editText3);
        password = findViewById(R.id.editText4);
        Button registerButton = findViewById(R.id.button2);
        TextView signIn = findViewById(R.id.textView2);
        userDB = new RegistrationHelper(this);


        /**
         * if username does not exist, register user, otherwise prompt a message
         */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")){
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkuser = userDB.checkUsername(user);
                    if(checkuser == false){
                        Boolean insert = userDB.insertData(user,pass);
                        if(insert == true){
                            Toast.makeText(RegisterActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }else{
                            //Toast.makeText(RegisterActivity.this, "User already exist, please sign in", Toast.LENGTH_SHORT).show();
                            Snackbar.make(username, "User already exist, please sign in", Snackbar.LENGTH_LONG).show();
                        }
                    }else{
                        //Toast.makeText(RegisterActivity.this, "User already exist, please sign in", Toast.LENGTH_SHORT).show();
                        Snackbar.make(username, "User already exist, please sign in", Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });

        /**
         * click listener for sign in button
         */
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
