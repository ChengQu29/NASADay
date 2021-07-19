package com.example.nasaday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * This class controls what detail information to display when the user clicked on an item on the recyclerView
 */
public class NasaDayDetailActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_day_detail);

        //getIntent() is defined in the AppCompatActivity
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");

        nameTextView = findViewById(R.id.NasaDay_topic);
        descriptionTextView = findViewById(R.id.NasaDay_topic_description);

        nameTextView.setText(name);
        descriptionTextView.setText(description);

    }
}
