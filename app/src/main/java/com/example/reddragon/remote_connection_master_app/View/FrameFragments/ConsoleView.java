package com.example.reddragon.remote_connection_master_app.View.FrameFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.reddragon.remote_connection_master_app.Model.Network.SSHManager;
import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.ProfileListAdapter;

import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.ipAddArray;
import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.keyAddArray;
import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.passAddArray;
import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.portAddArray;
import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.userAddArray;


/**
 * Created by RedDragon on 2/7/17.
 */

public class ConsoleView extends Fragment implements AdapterView.OnItemSelectedListener{

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

    private Spinner userSpn;
    private static CharSequence[] userOption;

    private String logon = "~root#";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.console, container, false);

        consoleListRV = (RecyclerView) view.findViewById(R.id.command_list_rv);

        display = (TextView) view.findViewById(R.id.console_tv);
        display.setMovementMethod(new ScrollingMovementMethod());

        entComTxt = (EditText) view.findViewById(R.id.console_et);
        ipTxt = (EditText) view.findViewById(R.id.enter_ip_console_et);
        portTxt = (EditText) view.findViewById(R.id.enter_port_console_et);

        userSpn = (Spinner) view.findViewById(R.id.console_host_lv);
        conBtn = (Button) view.findViewById(R.id.connect_btn);
        sendBtn = (Button) view.findViewById(R.id.send_btn);

        initiateConListRV();

        // initiate SSH Manager Class

        connectIP = ipTxt.getText().toString();

        sshMan = new SSHManager(username, password, connectIP, "", rsaKey, port);

        initiateButtonAction(conBtn, R.id.connect_btn);
        initiateButtonAction(sendBtn, R.id.send_btn);

        initiateUserSpinner();

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

                if(portTxt.getText().toString().isEmpty()){port = 22;
                }else{ port = Integer.valueOf(portTxt.getText().toString()); }

                Log.d("Log port value :", "test port: " +port);
                display.setText( display.getText() + "\n" +logon +"  " +sshMan.connect() );
                break;

            case R.id.send_btn:
                display.setText(display.getText() + "\n" +logon +"  "
                        +sshMan.sendCommand(entComTxt.getText().toString()) );
                break;

        }
            }
        });
    }

    private void initiateUserSpinner(){

        userOption = userAddArray.toArray(new CharSequence[userAddArray.size()]);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence> (
                getContext(), android.R.layout.simple_spinner_item, userOption);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears

        userSpn.setAdapter(adapter); // Apply the adapter to the spinner
        userSpn.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(!userAddArray.get(position).isEmpty()){
            Log.d("test", " user:" +userAddArray.get(position));
            username = userAddArray.get(position);
            logon = "~" +username +"#";
            display.setText(logon);
        }
        if(!passAddArray.get(position).isEmpty()){
            Log.d("test", " pass:" +passAddArray.get(position));
            password = passAddArray.get(position); }
        if(!keyAddArray.get(position).isEmpty()){
            Log.d("test", " key:" +keyAddArray.get(position));
            rsaKey = keyAddArray.get(position); }
//        if(!ipAddArray.get(position).isEmpty()){ ipTxt.setText(ipAddArray.get(position)); }
//        if(!portAddArray.get(position).isEmpty()){ portTxt.setText(portAddArray.get(position)); }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
