package com.example.reddragon.remote_connection_master_app.View.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;

import java.util.ArrayList;

/**
 * Created by RedDragon on 2/8/17.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {

    public static ArrayList<String> ipAddArray = new ArrayList<>(), portAddArray = new ArrayList<>();
    private EditText editHostName;
    private ImageButton editHostButton;

    private MainContainerAdapter adapter = new MainContainerAdapter();
    private RecyclerView preConnectRecycler;

    public CardViewHolder(View itemView) {
        super(itemView);

        editHostName = (EditText) itemView.findViewById(R.id.hostname_et);
        editHostButton = (ImageButton) itemView.findViewById(R.id.edit_hostname_btn);
        preConnectRecycler = (RecyclerView) itemView.findViewById(R.id.main_recycler);

        editHostName.setEnabled(false);

        editHostButton.setOnTouchListener(new ImageButton.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editHostName.setEnabled(true);
                editHostName.requestFocus();
                return false;
            }
        });

        editHostName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                addHostName(getAdapterPosition());
                Log.d("test on focus: ", "" +getAdapterPosition()+ " "+ipAddArray.size()
                        + " " +ipAddArray.get(getAdapterPosition()) +" "
                        +ipAddArray.get(getAdapterPosition()));

                v.setEnabled(false);
            }
        });



    }

    public void addHostName(int pos){


        String hostName = editHostName.getText().toString();

        if(hostName.contains(":")) {
            String[] parts = hostName.split(":");

            ipAddArray.set(pos, parts[0]);
            portAddArray.set(pos, parts[1]);

        }else{
            ipAddArray.set(pos, hostName);
            portAddArray.set(pos, "22");
        }

    }

    public void setHostAddress(int position){
        if(ipAddArray.size() > 0){
            editHostName.setText(ipAddArray.get(position));
        }
    }



}
