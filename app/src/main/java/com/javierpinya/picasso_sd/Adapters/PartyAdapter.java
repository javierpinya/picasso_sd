package com.javierpinya.picasso_sd.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javierpinya.picasso_sd.R;
import com.squareup.picasso.Picasso;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder> {

    private Context context;
    private int[] parties;
    private int layout;

    public PartyAdapter(Context context, int[] parties, int layout){
        this.context = context;
        this.parties = parties;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(parties[position]).fit().placeholder(R.drawable.ic_launcher_background).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return parties.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = (ImageView)itemView.findViewById(R.id.imageViewLayout);
        }
    }
}
