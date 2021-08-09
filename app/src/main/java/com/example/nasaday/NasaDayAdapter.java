package com.example.nasaday;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapater class - defines what data to be displayed in the RecyclerView and how to display the data
 */

public class NasaDayAdapter extends RecyclerView.Adapter<NasaDayAdapter.NasaDayViewHolder> {

    private ArrayList<NasaDay> nasaday;
    SQLiteDatabase db;
    Context context;

    //ViewHolder class to hold the view
    public class NasaDayViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout containerView;
        private TextView textView;


        //constructor, takes only one parameter view
        NasaDayViewHolder(View view) {
            super(view);
            containerView = view.findViewById(R.id.nasaday_row);
            textView = view.findViewById(R.id.nasaday_row_text_view);
            //attach a clicklistener to the containerview
            containerView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String current = (String) containerView.getTag();
                    //create intent
                    Intent intent = new Intent(v.getContext(), NasaDayDetailActivity.class);
                    intent.putExtra("date", current);
                    //intent.putExtra("description", current.getDescription());
                    //pass intent
                    v.getContext().startActivity(intent);
                }
            });

            containerView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    String current = (String) containerView.getTag();
                    //create intent
                    int s = getAbsoluteAdapterPosition();
                    System.out.println("position is:" + s);

                    //delete the item from database
                    deleteNasaDay(nasaday.get(s).getId());
                    //remove the item from the recyclerView
                    nasaday.remove(s);
                    notifyDataSetChanged();

                    return true;
                }
            });
        }
    }

    /**
    protected void showAlert(int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete this NasaDay ?")
                .setMessage("You can delete this item by clicking delete")
                .setNegativeButton("Delete", (click, b) -> {
                    deleteNasaDay(position);
                    nasaday.remove(position-1);
                    notifyDataSetChanged();
                })
                .setNeutralButton("Dismiss", (click, b) ->{})
                .create().show();
    }*/

    protected void deleteNasaDay(long n){
        MyOpener dbOpener = new MyOpener(context);
        db = dbOpener.getWritableDatabase();
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(n)});
    }

    //constructor to take date data
    NasaDayAdapter(Context context, ArrayList<NasaDay> nasaday){
        this.context = context;
        this.nasaday = nasaday;
        System.out.println("nasaday size currently is: " + nasaday.size()); // no data persistence yet, have to use database here
    }

    /*
    //hard coded for now, this will be updated in the next iteration
    private List<String> nasaday = Arrays.asList(
            "2020-07-02","2020-07-03","2020-07-04", "2020-07-05", "2020-07-06", "2020-07-07", "2020-07-08", "2020-07-09", "2020-07-10", "2020-07-11", "2020-07-12"
    );*/

    @NonNull
    @Override
    public NasaDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the xml file to java view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nasaday_row, parent, false);
        //return a new ViewHolder
        return new NasaDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NasaDayViewHolder holder, int position){
        String current = nasaday.get(position).getDate();
        //set the name object to be the text of the row
        holder.textView.setText(current);
        //give access of the current item to the viewHolder
        holder.containerView.setTag(current);
    }
    /**
     *
     * @return how many rows to display
     */
    @Override
    public int getItemCount() {
        return nasaday.size();
    }
}
