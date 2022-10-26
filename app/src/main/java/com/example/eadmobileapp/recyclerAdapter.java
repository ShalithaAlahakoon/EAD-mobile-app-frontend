package com.example.eadmobileapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eadmobileapp.models.Station;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Station> stationList;

    public recyclerAdapter(ArrayList<Station> stationList) {
        this.stationList = stationList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name,area,address,owner ,openClose ,octane92,octane95,diesel,superdiesel;


        public MyViewHolder(final View view) {
            super(view);
            name = view.findViewById(R.id.name);
            area = view.findViewById(R.id.area);
            address = view.findViewById(R.id.address);
            owner = view.findViewById(R.id.owner);
            openClose = view.findViewById(R.id.openclose);
            octane92 = view.findViewById(R.id.octane92);
            octane95 = view.findViewById(R.id.octane95);
            diesel = view.findViewById(R.id.diesel);
            superdiesel = view.findViewById(R.id.superdiesel);
        }
    }
    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String name = stationList.get(position).getStationName();
        String area = stationList.get(position).getStationArea();
        String address = stationList.get(position).getStationAddress();
        String owner = stationList.get(position).getOwner();
        String openClose = stationList.get(position).isOpenClose() ? "Open" : "Close";

       //check if the station has fuel types
        for (int i = 0; i < stationList.get(position).getFuelTypes().size(); i++) {
            if (stationList.get(position).getFuelTypes().get(i).fuelType.equals("92 octane")) {
                holder.octane92.setBackgroundColor(0xFF00FF00);
            }
            if (stationList.get(position).getFuelTypes().get(i).fuelType.equals("95 octane")) {
                holder.octane95.setBackgroundColor(0xFF00FF00);
            }
            if (stationList.get(position).getFuelTypes().get(i).fuelType.equals("Diesel")) {
                holder.diesel.setBackgroundColor(0xFF00FF00);
            }
            if (stationList.get(position).getFuelTypes().get(i).fuelType.equals("Super Diesel")) {
                holder.superdiesel.setBackgroundColor(0xFF00FF00);
            }
        }

        holder.name.setText(name);
        holder.area.setText(area);
        holder.address.setText(address);
        holder.owner.setText(owner);
        holder.openClose.setText(openClose);

    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }
}
