package com.example.nasaday;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

/**
 * This class instantiate recyclerView and set the adapter to display data
 */
public class ToolBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //recyclerView is used instead of listView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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

        //instantiate recyclerView
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new NasaDayAdapter(); //set adapter
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

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
                openToDoActivity();
                message = "You clicked on item 1";
                break;
            case R.id.item2:
                openNasaDayActivity();
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

    // Needed for the OnNavigationItemSelected interface:
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        String message = null;

        switch(item.getItemId())
        {
            case R.id.item1:
                openToDoActivity();
                message = "You clicked on item1";
                break;
            case R.id.item2:
                openNasaDayActivity();
                message = "You clicked on feeling lucky";
                break;
            case R.id.item3:
                openMainActivity();
                message = "You clicked on quit";
                break;
            case R.id.help_item:
                openAlertDialogue();
                message = "You clicked on help";
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        Toast.makeText(this, "NavigationDrawer: " + message, Toast.LENGTH_LONG).show();
        return false;
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
    //pass intent
    private void openToDoActivity(){
        //todo
    }

    // this is hard coded for now, this will be updated in the next iteration
    private void openNasaDayActivity(){
        Intent intent = new Intent(this, NasaDayDetailActivity.class);
        intent.putExtra("name", "2018-05-05");
        //intent.putExtra("description", current.getDescription());
        //pass intent
        startActivity(intent);
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 500);
    }
}
