package com.example.skuadproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skuadproject.Models.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable {
    private List<Result> listItems;
    private List<Result> listItemsFiltered;
    private Context context;
    private CustomAdapterListener listener;

    public CustomAdapter(List<Result> listItems, Context context, CustomAdapterListener listener) {
        this.listItems = listItems;
        this.listItemsFiltered = listItems;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = listItemsFiltered.get(position);

        holder.name.setText(result.getName());
        holder.opening_hours.setText(result.getOpeningHours() != null &&
                result.getOpeningHours().equalsIgnoreCase("True") ? "Open": "Closed");
        String str = result.getItemType().replace("\"","");
        String itemType = str.substring(0, 1).toUpperCase() + str.substring(1);
        holder.item_type.setText(itemType);
        holder.item_buisness_status.setText(result.getBusinessStatus());
        holder.item_address.setText(result.getItemAddress());
        holder.item_rating.setText(result.getRating());

//        holder.

//        if (result.getOpeningHours().getOpen_now().equals(true))
//            holder.opening_hours.setText("OPEN");
//        else
//            holder.opening_hours.setText("CLOSED");

        Picasso.get().load(result.getIcon()).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return listItemsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listItemsFiltered = listItems;
                } else {
                    List<Result> filteredList = new ArrayList<>();
                    for (Result row : listItems) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    listItemsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listItemsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listItemsFiltered = (List<Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView name, opening_hours, item_type, item_buisness_status, item_address, item_rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            icon = itemView.findViewById(R.id.item_icon);
            opening_hours = itemView.findViewById(R.id.item_open_time);
            item_type = itemView.findViewById(R.id.item_type);
            item_buisness_status = itemView.findViewById(R.id.item_buisness_status);
            item_address = itemView.findViewById(R.id.item_address);
            item_rating = itemView.findViewById(R.id.item_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onItemSelected(listItemsFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface CustomAdapterListener {
        void onItemSelected(Result result);
    }
}
