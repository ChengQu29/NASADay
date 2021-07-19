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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("ReserveName", "");
        EditText typeField = findViewById(R.id.editText1);
        typeField.setText(savedString);

        Button loginButton = findViewById(R.id.button1);
        TextView text1 = findViewById(R.id.textView1);

        //if login button is clicked, go to next page
        loginButton.setOnClickListener(click -> {
            openToolBarActivity();
        });

        //if the register text is clicked, go to the page for registration
        text1.setOnClickListener( click -> openRegisterActivity());

    }

    private void openToolBarActivity() {
        Intent intent = new Intent(this, ToolBarActivity.class);
        startActivity(intent);
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

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
