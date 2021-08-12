package com.example.nasaday;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapater class - defines what data to be displayed in the RecyclerView and how to display the data
 */

public class NasaDayAdapter extends RecyclerView.Adapter<NasaDayAdapter.NasaDayViewHolder> {

    private ArrayList<NasaDay> nasaday;
    SQLiteDatabase db;
    private Context context;

    //constructor to take input data
    NasaDayAdapter(Context mContext, ArrayList<NasaDay> nasaday){
        this.context = mContext;
        this.nasaday = nasaday;
        //System.out.println("nasaday size currently is: " + nasaday.size()); // no data persistence yet, have to use database here
    }

    //ViewHolder class to hold the view
    public class NasaDayViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout containerView;
        private TextView textViewDate;
        private TextView textViewTitle;
        private ImageView imageView;


        //constructor, takes only one parameter view
        public NasaDayViewHolder(View view) {
            super(view);
            containerView = view.findViewById(R.id.nasaday_row);
            textViewTitle = view.findViewById(R.id.nasaday_row_title);
            textViewDate = view.findViewById(R.id.nasaday_row_date);
            imageView = view.findViewById(R.id.nasaday_row_imageView);

            //attach a clicklistener to the containerview
            containerView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String current = (String) containerView.getTag();
                    //create intent
                    Intent intent = new Intent(v.getContext(), NasaDayDetailActivity.class);
                    intent.putExtra("date", current);
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
                    //System.out.println("position is:" + s);

                    showAlert(s, view);

                    return true;
                }
            });
        }
    }

    /**
     * function to show alert dialogue window
     * @param position
     */
    protected void showAlert(int position, View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Delete this NasaDay ?")
                .setMessage("You can delete this item by clicking delete")
                .setNegativeButton("Delete", (click, b) -> {
                    deleteNasaDay(nasaday.get(position).getId()); //delete from the DB
                    nasaday.remove(position); //remove the item from the recyclerView
                    notifyDataSetChanged();
                })
                .setNeutralButton("Dismiss", (click, b) ->{})
                .create().show();
    }

    /**
     * Function to delete an entry from the DB
     * @param n
     */
    protected void deleteNasaDay(long n){
        MyOpener dbOpener = new MyOpener(context);
        db = dbOpener.getWritableDatabase();
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(n)});
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
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.nasaday_row, parent, false);
        //return a new ViewHolder
        return new NasaDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NasaDayViewHolder holder, int position){
        String current = nasaday.get(position).getDate();
        String title = nasaday.get(position).getTitle();

        //convert from byte array stored in the DB back to bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(nasaday.get(position).getImage(), 0, nasaday.get(position).getImage().length);
        //set the title
        holder.textViewTitle.setText(title);
        //set the name object to be the text of the row
        holder.textViewDate.setText(current);
        //set the imageView
        holder.imageView.setImageBitmap(bitmap);

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
