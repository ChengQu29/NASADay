package com.example.nasaday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class controls what detail information to display when the user clicked on an item on the recyclerView
 */
public class NasaDayDetailActivity extends AppCompatActivity {

    private static final String TAG ="NasaDayApp";
    private TextView nameTextView;
    private TextView descriptionTextView;
    ImageView imageView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_day_detail);

        //getIntent() is defined in the AppCompatActivity
        String name = getIntent().getStringExtra("name");
        //String description = getIntent().getStringExtra("description");

        nameTextView = findViewById(R.id.NasaDay_topic);
        descriptionTextView = findViewById(R.id.NasaDay_topic_description);
        imageView = findViewById(R.id.NasaDay_image);
        progressBar = findViewById(R.id.progressBar);

        nameTextView.setText(name);
        //descriptionTextView.setText(description);

        nasaDayQuery req = new nasaDayQuery();
        req.execute("https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date="+name);
    }

    /**
     * AsyncTask to fetch api
     */
    class nasaDayQuery extends AsyncTask<String, Integer, String> { //<Param, Progress, Result>

        String description;
        String imageurl;
        @Override
        protected String doInBackground(String...args){
            Log.i(TAG, "doInBackground: string passed: "+args[0]);
            Log.i(TAG, "Thread: " + Thread.currentThread().getName() + getStatus());

            try{
                //create a URL object of what server to contact:
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //JSON reading:
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string
                publishProgress(25);
                Thread.sleep(500);

                // convert string to JSON:
                JSONObject nasaDayInfo = new JSONObject(result);

                //get description of the nasa day
                String explanation = nasaDayInfo.getString("explanation");
                publishProgress(50);
                description = explanation;
                Log.i("NasaDayDetailActivity", "Explanation: " + explanation) ;

                //get image
                String imageurl = nasaDayInfo.getString("url");
                Bitmap image = null;
                URL url2 = new URL(imageurl);
                urlConnection = (HttpURLConnection) url2.openConnection();
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    image = BitmapFactory.decodeStream(urlConnection.getInputStream());
                }

                FileOutputStream outputStream = openFileOutput(image + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
                publishProgress(75);

                imageView.setImageBitmap(image);

            }catch(Exception e){
                Log.e("Error", e.getMessage());
            }

            String fname = "image.png";

            File file = getBaseContext().getFileStreamPath(fname);
            if (file.exists()){
                Log.i("Filename", fname + " was downloaded");
            }else{
                Log.i("Filename","File was stored locally");
            }

            return description;
        }

        @Override
        public void onProgressUpdate(Integer ... values){
            progressBar.setProgress(values[0]+1);
            Log.i(TAG, "onProgressUpdate: " + Thread.currentThread().getName());
        }

        @Override
        public void onPostExecute(String fromDoInBackground){
            descriptionTextView.setText(description);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
