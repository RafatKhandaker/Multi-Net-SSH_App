package com.example.reddragon.remote_connection_master_app.View.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.reddragon.remote_connection_master_app.R;

/**
 * Created by RedDragon on 2/8/17.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {

    private EditText editHostName;

    public CardViewHolder(View itemView) {
        super(itemView);
        editHostName = (EditText)itemView.findViewById(R.id.hostname_et);
    }

    public void getHostName(){
        String hostName = editHostName.getText().toString();

        if(hostName.contains(":")) {
            String[] parts = hostName.split(":");
            String ip = parts[0];
            String port = parts[1];
        }

    }



}
