package com.aslam.attendancesystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private final Context context;
    private final List<Users> items;

    public ItemAdapter(Context context, List<Users> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_display, parent, false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        final Users item = items.get(position);
        holder.showName.setText(item.name);
        holder.showId.setText(item.id);
        holder.showGender.setText(item.gender);
        holder.showPhone.setText(item.phone);
        holder.showEmail.setText(item.email);
        holder.showAddress.setText(item.address);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView showName, showId, showGender, showPhone, showEmail, showAddress;

        public ViewHolder(View itemView) {
            super(itemView);

            showName = itemView.findViewById(R.id.showName);
            showId = itemView.findViewById(R.id.showId);
            showGender = itemView.findViewById(R.id.showGender);
            showPhone = itemView.findViewById(R.id.showPhone);
            showEmail = itemView.findViewById(R.id.showEmail);
            showAddress = itemView.findViewById(R.id.showAddress);

        }
    }
}
