package com.example.nasaday;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class instantiate recyclerView and set the adapter to display data
 */
public class ToolBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //recyclerView is used instead of listView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SQLiteDatabase db;

    private ArrayList<NasaDay> nasaday = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);

        //for toolbar;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //for navigationDrawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadFromDatabase();

        //instantiate recyclerView
        recyclerView = findViewById(R.id.recycler_view);
        //pass the context in for showAlert, pass nasaday in for
        adapter = new NasaDayAdapter(getApplicationContext(), nasaday); //set adapter, inflation of rows in the recycler view happens in the adapter
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        /*
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
        });*/
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
            case R.id.item4:
                message = "You are now on favorites";
                break;
            case R.id.help_item:
                openHelpDialogue();
                message = "You clicked on help";
                break;
            case R.id.contact_me:
                openAlertDialogue();
                message = "You clicked on the overflow menu";
                break;
            case R.id.sign_out:
                openMainActivity();
                message = "You clicked on sign out";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return true;
    }

    // Needed for the OnNavigationItemSelected interface:
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        String message = null;

        switch(item.getItemId())
        {
            case R.id.item1:
                showDatePickerDialog();
                message = "You clicked on Date Picker";
                break;
            case R.id.item2:
                openRandomNasaDayActivity();
                message = "You clicked on feeling lucky";
                break;
            case R.id.item4:
                message = "You are now on favorites";
                break;
            case R.id.help_item:
                openHelpDialogue();
                message = "You clicked on help";
                break;
            case R.id.contact_me:
                openAlertDialogue();
                message = "You clicked on the overflow menu";
                break;
            case R.id.sign_out:
                openMainActivity();
                message = "You clicked on sign out";
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        Toast.makeText(this, "NavigationDrawer: " + message, Toast.LENGTH_LONG).show();
        return false;
    }

    /**
     * open help dialogue
     */
    private void openHelpDialogue(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Instructions")
                .setMessage("1. Click on dates to see detail.\n2. Long click on dates to delete.\n3. Click add button to save to favorites.\n4. Click rocket icon to try your luck.")
                .create().show();
    }

    /**
     * Altert dialogue
     */
    private void openAlertDialogue(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Contact me")
                .setMessage("qu000026@algonquinlive.com")
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
            int realMonth = month+1;
            datePicked = year+"-"+realMonth+"-"+day;
            if (year >= 1995){ //nasa photo of the day api only supports date range from 1995-now
                Intent intent = new Intent(getContext(), NasaDayDetailActivity.class);
                intent.putExtra("date", datePicked);
                startActivity(intent);
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setTitle("Date out of range")
                        .setMessage("Nasa photo of the day starts after 1995.")
                        .create().show();
            }
        }
    }

    /**
     * Generate a random Nasa photo of the day
     */
    private void openRandomNasaDayActivity(){
        Intent intent = new Intent(this, NasaDayDetailActivity.class);
        String randomD = generateRandomDate();
        intent.putExtra("date", randomD);
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
     * function for going back to mainActivity
     */
    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 500);
    }

    private void loadFromDatabase(){
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        String [] columns = {MyOpener.COL_ID, MyOpener.COL_DATE};

        //query all the results from the database:  (Cursors are a storage object that contains rows from a query.)
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int typeColIndex = results.getColumnIndex(MyOpener.COL_DATE);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String date = results.getString(typeColIndex);
            long id = results.getLong(idColIndex);

            //add the new message to the array list:
            nasaday.add(new NasaDay(date,id));
        }

        //At this point, the contactsList array has loaded every row from the cursor.

    }
}
