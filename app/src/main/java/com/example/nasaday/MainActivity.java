package com.example.nasaday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * class for the login page
 */
public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    EditText username, password;
    Button loginButton;
    RegistrationHelper userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("ReserveName", "");

        username = findViewById(R.id.editText1);
        username.setText(savedString);
        password = findViewById(R.id.editText2);
        loginButton = findViewById(R.id.button1);
        userDB = new RegistrationHelper(this);

        TextView text1 = findViewById(R.id.textView1);

        //if login button is clicked, go to next page
        loginButton.setOnClickListener(click -> {
            saveSharedPrefs(username.getText().toString());
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkUserPass = userDB.checkUsernamePassword(user,pass);
                    if(checkUserPass==true){
                        Toast.makeText(MainActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        openFavPageActivity();
                    }else {
                        Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //if the register text is clicked, go to the page for registration
        text1.setOnClickListener( click -> openRegisterActivity());

    }

    private void openFavPageActivity() {
        Intent intent = new Intent(this, FavPageActivity.class);
        startActivity(intent);
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //sharedprefs
    private void saveSharedPrefs(String stringToSave) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ReserveName", stringToSave);
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();

        EditText typeField = findViewById(R.id.editText1);
        saveSharedPrefs(typeField.getText().toString());

        Log.d("First Activity", "In onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
