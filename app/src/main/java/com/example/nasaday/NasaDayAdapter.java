package com.example.nasaday;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class NasaDayAdapter extends RecyclerView.Adapter<NasaDayAdapter.NasaDayViewHolder> {
    public static class NasaDayViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout containerView;
        private TextView textView;

        NasaDayViewHolder(View view) {
            super(view);
            containerView = view.findViewById(R.id.nasaday_row);
            textView = view.findViewById(R.id.nasaday_row_text_view);

            containerView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    NasaDay current = (NasaDay) containerView.getTag();
                    Intent intent = new Intent(v.getContext(), NasaDayDetailActivity.class);
                    intent.putExtra("name", current.getName());
                    intent.putExtra("description", current.getDescription());

                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    private List<NasaDay> nasaday = Arrays.asList(
            new NasaDay("Mars", "Mars is the fourth planet from the Sun and the second-smallest planet in the Solar System."),
            new NasaDay("Moon", "The Moon is Earth's only natural satellite."),
            new NasaDay("Jupiter", "Jupiter is the fifth planet from the Sun and the largest in the Solar System.")
    );
    @NonNull
    @Override
    public NasaDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nasaday_row, parent, false);

        return new NasaDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NasaDayViewHolder holder, int position){
        NasaDay current = nasaday.get(position);
        holder.textView.setText(current.getName());
        holder.containerView.setTag(current);
    }

    @Override
    public int getItemCount() {
        return nasaday.size();
    }
}
