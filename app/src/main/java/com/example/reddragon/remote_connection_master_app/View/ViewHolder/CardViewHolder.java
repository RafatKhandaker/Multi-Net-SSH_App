package com.example.reddragon.remote_connection_master_app.View.ViewHolder;


import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;

import static com.example.reddragon.remote_connection_master_app.MainActivity.Connect_Count;
import static com.example.reddragon.remote_connection_master_app.MainActivity.ipAddArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.ipArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.portAddArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.portArray;

/**
 * Created by RedDragon on 2/8/17.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {

    MainContainerAdapter adapter;

    private EditText editHostName;
    private ImageButton editHostButton;


    public CardViewHolder(View itemView) {
        super(itemView);

        adapter = new MainContainerAdapter();

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
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addHostName(getAdapterPosition());
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
        if(ipAddArray.size() > 2 && Connect_Count > 1){
            if(!ipAddArray.get(position).equals("")) {
                editHostName.setText(ipAddArray.get(position)
                        + ":" + portAddArray.get(position));
            }
        }
    }



}
