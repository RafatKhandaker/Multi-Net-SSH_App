package com.example.reddragon.remote_connection_master_app.View.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.reddragon.remote_connection_master_app.R;

import java.util.ArrayList;

/**
 * Created by RedDragon on 2/8/17.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {

    private EditText editHostName;
    private ImageButton editHostButton;

    public CardViewHolder(View itemView) {
        super(itemView);

        editHostName = (EditText)itemView.findViewById(R.id.hostname_et);
        editHostButton = (ImageButton)itemView.findViewById(R.id.edit_hostname_btn);

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
                v.setEnabled(false);
            }
        });
    }

    public void setHostName(int pos){

        ArrayList<String> arrHost = new ArrayList<>();
        ArrayList<String> arrPort = new ArrayList<>();

        String ip, port;
        String hostName = editHostName.getText().toString();

        if(hostName.contains(":")) {
            String[] parts = hostName.split(":");
             ip = parts[0];
             port = parts[1];
        }else{
            ip = hostName;
            port = "22";
        }

        arrHost.add(ip);
        arrPort.add(port);

        Log.d("test ip port : ", ""
                +arrHost.get(arrHost.size() -1) +" "+arrPort.get(arrPort.size() -1));



    }



}
