package com.example.reddragon.remote_connection_master_app.View.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;

import java.util.ArrayList;

import static com.example.reddragon.remote_connection_master_app.MainActivity.preConDatabase;
import static com.example.reddragon.remote_connection_master_app.View.ViewHolder.CardViewHolder.ipAddArray;
import static com.example.reddragon.remote_connection_master_app.View.ViewHolder.CardViewHolder.portAddArray;


/**
 * Created by RedDragon on 3/6/17.
 */

public class AddCardViewHolder extends RecyclerView.ViewHolder {

    MainContainerAdapter adapter;
    ImageButton addNewCard;

    public AddCardViewHolder(View itemView) {
        super(itemView);

        adapter = new MainContainerAdapter();

        addNewCard = (ImageButton) itemView.findViewById(R.id.add_hostname_btn);

        addNewCard.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                ipAddArray.add("");
                portAddArray.add("");
                adapter.updateData();
            }
        });
    }

}
