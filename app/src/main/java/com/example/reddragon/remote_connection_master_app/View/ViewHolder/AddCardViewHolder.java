package com.example.reddragon.remote_connection_master_app.View.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;


import static com.example.reddragon.remote_connection_master_app.MainActivity.ipAddArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.portAddArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.preConDatabase;


/**
 * Created by RedDragon on 3/6/17.
 */

public class AddCardViewHolder extends RecyclerView.ViewHolder {

    MainContainerAdapter adapter = new MainContainerAdapter();
    ImageButton addNewCard;
    ImageButton saveDataBtn;

    public AddCardViewHolder(View itemView) {
        super(itemView);

        addNewCard = (ImageButton) itemView.findViewById(R.id.add_hostname_btn);
        saveDataBtn = (ImageButton) itemView.findViewById(R.id.save_host_icon);

        addNewCard.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                ipAddArray.add("");
                portAddArray.add("");
                adapter.updateData();
            }
        });

        saveDataBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 0;
                while(x < (ipAddArray.size())){
                    preConDatabase.insertData(ipAddArray.get(x), portAddArray.get(x));
                    x++;
                }
            }
        });
    }

}
