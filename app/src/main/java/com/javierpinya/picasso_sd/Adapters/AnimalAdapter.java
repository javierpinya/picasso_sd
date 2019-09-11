package com.javierpinya.picasso_sd.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javierpinya.picasso_sd.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {

    private Context context;
    private String[] animals;
    private int layout;

    public AnimalAdapter(Context context, String[] animals, int layout) {
        this.context = context;
        this.animals = animals;
        this.layout = layout;
    }

    @NonNull
    @Override
    public AnimalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalAdapter.ViewHolder holder, int position) {

        Picasso.get().load(animals[position]).fit().placeholder(R.drawable.ic_launcher_background).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {
                //holder.image.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return animals.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = (ImageView)itemView.findViewById(R.id.imageViewLayout);
        }
    }
}
