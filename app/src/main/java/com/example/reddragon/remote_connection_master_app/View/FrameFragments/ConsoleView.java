package com.example.reddragon.remote_connection_master_app.View.FrameFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.reddragon.remote_connection_master_app.Model.Network.SSHManager;
import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.ProfileListAdapter;


/**
 * Created by RedDragon on 2/7/17.
 */

public class ConsoleView extends Fragment {

    private RecyclerView consoleListRV;
    private ProfileListAdapter profileListAdapter;
    private RecyclerView.LayoutManager conListLayMan;

    private TextView display;

    private EditText ipTxt;
    private EditText portTxt;
    private EditText entComTxt;

    private Button conBtn;
    private Button sendBtn;

    private SSHManager sshMan;

    private String username = "";
    private String password = "";
    private String rsaKey = "";
    private String connectIP = "";
    private int port = 22;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.console, container, false);

        consoleListRV = (RecyclerView) view.findViewById(R.id.command_list_rv);

        display = (TextView) view.findViewById(R.id.console_tv);

        entComTxt = (EditText) view.findViewById(R.id.console_et);
        ipTxt = (EditText) view.findViewById(R.id.enter_ip_console_et);
        portTxt = (EditText) view.findViewById(R.id.enter_port_console_et);

        conBtn = (Button) view.findViewById(R.id.connect_btn);
        sendBtn = (Button) view.findViewById(R.id.send_btn);

        initiateConListRV();

        // initiate SSH Manager Class

        connectIP = ipTxt.getText().toString();

        if(portTxt.getText().toString().isEmpty()){
            port = 22;
        }else{ port = Integer.valueOf(portTxt.getText().toString()); }

        sshMan = new SSHManager(username, password, connectIP, "", rsaKey, port);

        initiateButtonAction(conBtn, R.id.connect_btn);
        initiateButtonAction(sendBtn, R.id.send_btn);

        return view;
    }

    private void initiateConListRV(){
        conListLayMan = new LinearLayoutManager(getContext());
        profileListAdapter = new ProfileListAdapter();
        consoleListRV.setLayoutManager(conListLayMan);
        consoleListRV.setAdapter(profileListAdapter);
    }

    private void initiateButtonAction(View view, final int r){

        view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        switch(r){

            case R.id.connect_btn:
                display.setText( display.getText() +"\n" +sshMan.connect());
                break;
            case R.id.send_btn:
                display.setText(display.getText() + "\n"
                        +sshMan.sendCommand(entComTxt.getText().toString()) );
                break;

        }
            }
        });
    }

}
