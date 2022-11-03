package com.example.eadmobileapp;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Queue;
import com.example.eadmobileapp.models.Station;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Station> stationList;
    //get API interface
    API api = RetrofitClient.getInstance().getApi();
    private ArrayList<Queue> queueList;

    public recyclerAdapter(ArrayList<Station> stationList) {
        this.stationList = stationList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name,area,address,owner ,openClose ,octane92,octane95,diesel,superdiesel,txt_92octane_queue,txt_95octane_queue,txt_diesel_queue,txt_superdiesel_queue;


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
            txt_92octane_queue = view.findViewById(R.id.txt_92octane_queue);
            txt_95octane_queue = view.findViewById(R.id.txt_95octane_queue);
            txt_diesel_queue = view.findViewById(R.id.txt_diesel_queue);
            txt_superdiesel_queue = view.findViewById(R.id.txt_superdiesel_queue);
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
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = stationList.get(position).getStationName();
        String area = stationList.get(position).getStationArea();
        String address = stationList.get(position).getStationAddress();
        String owner = stationList.get(position).getOwner();
        String openClose = stationList.get(position).isOpenClose() ? "Open" : "Close";

        //check if the station has fuel types
        for (int i = 0; i < stationList.get(position).getFuelTypes().size(); i++) {
            if (stationList.get(position).getFuelTypes().get(i).fuelType.equals("92 Octane") && stationList.get(position).getFuelTypes().get(i).availability) {
                holder.octane92.setBackgroundColor(0xFF00FF00);
            }
            if (stationList.get(position).getFuelTypes().get(i).fuelType.equals("95 Octane") && stationList.get(position).getFuelTypes().get(i).availability) {
                holder.octane95.setBackgroundColor(0xFF00FF00);
            }
            if (stationList.get(position).getFuelTypes().get(i).fuelType.equals("Diesel") && stationList.get(position).getFuelTypes().get(i).availability) {
                holder.diesel.setBackgroundColor(0xFF00FF00);
            }
            if (stationList.get(position).getFuelTypes().get(i).fuelType.equals("Super Diesel")    && stationList.get(position).getFuelTypes().get(i).availability) {
                holder.superdiesel.setBackgroundColor(0xFF00FF00);
            }

        }

        holder.name.setText(name);
        holder.area.setText(area);
        holder.address.setText(address);
        holder.owner.setText(owner);
        holder.openClose.setText(openClose);

        //get queue list
        Call<List<Queue>> call = api.getQueues();
        call.enqueue(new retrofit2.Callback<List<Queue>>() {
            @Override
            public void onResponse(Call<List<Queue>> call, retrofit2.Response<List<Queue>> response) {
                queueList = (ArrayList<Queue>) response.body();
                //        //get queues for each name
                for (int i = 0; i < queueList.size(); i++) {
                    if (queueList.get(i).getStationName().equals(name)) {
                        System.out.println("queue stations station name = "+ queueList.get(i).getStationName());
                        System.out.println("fuel type = "+ queueList.get(i).getFuel_type());
                        if (queueList.get(i).getFuel_type().equals("92 Octane")) {
                            holder.txt_92octane_queue.setText(String.valueOf(queueList.get(i).getCount()));
                        }
                        if (queueList.get(i).getFuel_type().equals("95 Octane")) {
                            holder.txt_95octane_queue.setText(String.valueOf(queueList.get(i).getCount()));
                        }
                        if (queueList.get(i).getFuel_type().equals("Diesel")) {
                            holder.txt_diesel_queue.setText(String.valueOf(queueList.get(i).getCount()));
                        }
                        if (queueList.get(i).getFuel_type().equals("Super Diesel")) {
                            holder.txt_superdiesel_queue.setText(String.valueOf(queueList.get(i).getCount()));
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Queue>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });




    }


    @Override
    public int getItemCount() {
        return stationList.size();
    }
}
