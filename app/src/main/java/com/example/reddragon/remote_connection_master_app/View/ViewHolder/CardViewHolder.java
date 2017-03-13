package com.example.reddragon.remote_connection_master_app.View.ViewHolder;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.reddragon.remote_connection_master_app.R;

import java.util.ArrayList;

import static com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter.mainHostArray;

/**
 * Created by RedDragon on 2/8/17.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {

    public static ArrayList<String> ipAddArray = new ArrayList<>();
    public static ArrayList<String> portAddArray = new ArrayList<>();

    private EditText editHostName;
    private ImageButton editHostButton;


    public CardViewHolder(View itemView) {
        super(itemView);

        editHostName = (EditText) itemView.findViewById(R.id.hostname_et);
        editHostButton = (ImageButton) itemView.findViewById(R.id.edit_hostname_btn);

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
                v.setEnabled(false);
            }
        });

        editHostName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                addHostName(getAdapterPosition());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                addHostName(getAdapterPosition());
            }
        });

    }

    public void addHostName(int pos){
        String hostName = editHostName.getText().toString();

        if(hostName.contains(":")) {
            String[] parts = hostName.split(":");
            if(parts.length > 1) {
                ipAddArray.set(pos, parts[0]);
                portAddArray.set(pos, ("" + parts[1]));
            }

        }else{
            ipAddArray.set(pos, hostName);
            portAddArray.set(pos, "22");
        }

    }

    public void onBind(int position){
        if(ipAddArray.size() > 2 && mainHostArray.size() > 1){
            if(!ipAddArray.get(position).equals("")) {
                editHostName.setText(ipAddArray.get(position)
                        + ":" + portAddArray.get(position));
            }
        }
    }



}
