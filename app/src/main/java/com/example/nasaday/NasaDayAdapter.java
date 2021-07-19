package com.example.nasaday;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Arrays;
import java.util.List;

/**
 * Adapater class - defines what data to be displayed in the RecyclerView and how to display the data
 */

public class NasaDayAdapter extends RecyclerView.Adapter<NasaDayAdapter.NasaDayViewHolder> {
    //ViewHolder class to hold the view
    public static class NasaDayViewHolder extends RecyclerView.ViewHolder {

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
                    NasaDay current = (NasaDay) containerView.getTag();
                    //create intent
                    Intent intent = new Intent(v.getContext(), NasaDayDetailActivity.class);
                    intent.putExtra("name", current.getName());
                    intent.putExtra("description", current.getDescription());
                    //pass intent
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    //hard coded for testing features, this will be replaced in the next iteration
    private List<NasaDay> nasaday = Arrays.asList(
            new NasaDay("Mars", "Mars is the fourth planet from the Sun and the second-smallest planet in the Solar System, being larger than only Mercury. In English, Mars carries the name of the Roman god of war and is often referred to as the \"Red Planet\"."),
            new NasaDay("Moon", "The Moon is Earth's only natural satellite. At about one-quarter the diameter of Earth, it is the largest natural satellite in the Solar System relative to the size of its planet, the fifth largest satellite in the Solar System overall, and is larger than any known dwarf planet."),
            new NasaDay("Jupiter", "Jupiter is the fifth planet from the Sun and the largest in the Solar System. It is a gas giant with a mass more than two and a half times that of all the other planets in the Solar System combined, but slightly less than one-thousandth the mass of the Sun.")
    );

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
        NasaDay current = nasaday.get(position);
        //set the name object to be the text of the row
        holder.textView.setText(current.getName());
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
