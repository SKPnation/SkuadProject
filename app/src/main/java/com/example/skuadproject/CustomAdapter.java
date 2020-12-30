package com.example.skuadproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skuadproject.Models.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private List<Result> listItems;
    private Context context;

    public CustomAdapter(List<Result> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = listItems.get(position);

        holder.name.setText(result.getName());

//        if (result.getOpeningHours().getOpen_now().equals(true))
//            holder.opening_hours.setText("OPEN");
//        else
//            holder.opening_hours.setText("CLOSED");

        Picasso.get().load(result.getIcon()).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView name,opening_hours;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            icon = itemView.findViewById(R.id.item_icon);
            opening_hours = itemView.findViewById(R.id.item_open_time);
        }
    }
}
