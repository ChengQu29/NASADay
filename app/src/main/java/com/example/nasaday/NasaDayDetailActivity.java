package com.example.nasaday;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

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

        //for toolbar;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView sView = (SearchView)searchItem.getActionView(); sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    /**
     * this method responds to an item on the toolbar being selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch (item.getItemId()){
            case R.id.item1:
                showDatePickerDialog();
                message = "You clicked on Date Picker";
                break;
            case R.id.item2:
                openRandomNasaDayActivity();
                message = "You clicked on feeling lucky";
                break;
            case R.id.item3:
                openMainActivity();
                message = "You clicked on quit";
                break;
            case R.id.help_item:
                openAlertDialogue();
                message = "You clicked on the overflow menu";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * Altert dialogue
     */
    private void openAlertDialogue(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Instructions")
                .setMessage("Click on the date to load Nasa Photo of the day or click 'feeling lucking'!")
                .create().show();
    }

    /**
     * when user clicks the button/item, system calls the following method
     */
    private void showDatePickerDialog(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Fragment class for date picker
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        /**
         * When the user picked a date, go to NasaDayDetailActivity with that date picked
         * (show the Nasa photo of that date picked)
         * @param view
         * @param year
         * @param month
         * @param day
         */
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String datePicked = "";
            datePicked=year+"-"+month+"-"+day;
            Intent intent = new Intent(getContext(), NasaDayDetailActivity.class);
            intent.putExtra("name", datePicked);
            startActivity(intent);
        }
    }

    /**
     * Generate a random Nasa photo of the day
     */
    private void openRandomNasaDayActivity() {
        Intent intent = new Intent(this, NasaDayDetailActivity.class);
        String randomD = generateRandomDate();
        intent.putExtra("name", randomD);
        //intent.putExtra("description", current.getDescription());
        //pass intent
        startActivity(intent);
    }

    /**
     * Generate a random date between 1995-2021
     * NASA photo of the day only works for date between 1995-2021
     * @return
     */
    private String generateRandomDate() {

        String randomDate = createRandomDate(1995, 2021);
        return randomDate;
    }

    private String createRandomDate(int startYear, int endYear){
        String randomDate="";
        int day = createRandomIntBetween(1,28);
        int month = createRandomIntBetween(1,12);
        int year = createRandomIntBetween(startYear, endYear);
        randomDate=year+"-"+month+"-"+day;

        return randomDate;

    }

    private int createRandomIntBetween(int start, int end){
        return start + (int)Math.round(Math.random()*(end - start));
    }

    /**
     * class to go back to mainActivity
     */
    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 500);
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
