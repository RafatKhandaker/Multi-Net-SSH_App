package com.example.reddragon.remote_connection_master_app.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.ViewHolder.CardViewHolder;

import static com.example.reddragon.remote_connection_master_app.MainActivity.Connect_Count;


/**
 * Created by RedDragon on 2/8/17.
 */
public class MainContainerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public MainContainerAdapter() {}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         return new CardViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_card_holder, null));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CardViewHolder) holder).onBind(position);

    }


    @Override
    public int getItemCount() {
        return Connect_Count;
    }


}
