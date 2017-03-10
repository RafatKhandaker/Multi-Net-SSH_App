package com.example.reddragon.remote_connection_master_app.View;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.ViewHolder.AddCardViewHolder;
import com.example.reddragon.remote_connection_master_app.View.ViewHolder.CardViewHolder;

import java.util.ArrayList;

import static com.example.reddragon.remote_connection_master_app.MainActivity.ipArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.portArray;

/**
 * Created by RedDragon on 2/8/17.
 */
public class MainContainerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> tmpIPArray;
    private ArrayList<String> tmpPortArray;

    public static int dataSize = 1;

    public MainContainerAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("test view Type: ", "" + viewType + " " + dataSize);
        if (viewType != dataSize) {
            return new CardViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_card_holder, null));
        } else {

            return new AddCardViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.add_newcard_holder, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (ipArray != null) {
            if (position != ipArray.size()) {
                ((CardViewHolder) holder).addHostName(position);
                Log.d("test Position :", " " + position);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == dataSize + 1) {
            Log.d("test item view type: ", "" + dataSize + " " + position);
            return position;
        } else {
            return position;
        }

    }

    @Override
    public int getItemCount() {
        return dataSize + 1;    // test with
    }

    private int createDataSize() {
        if (ipArray == null) {
            return dataSize = 1;
        }
        return dataSize = ipArray.size() + 1;
    }

    public void updateData(){
        notifyDataSetChanged();
    }

    public void setIPArray() {
        ipArray = this.tmpIPArray;
    }

    public void setPortArray() {
        portArray = this.tmpPortArray;
    }
}
