package com.example.reddragon.remote_connection_master_app.View.FrameFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.ProfileListAdapter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.commandArrList;
import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.commandArray;
import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.keyAddArray;
import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.passAddArray;
import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.profilesDB;
import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.userAddArray;
import static com.example.reddragon.remote_connection_master_app.Controller.MainActivity.userArray;
import static com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.FrameViewHolder.CommandListViewHolder.commandListPosition;

/**
 * Created by RedDragon on 2/7/17.
 */

public class FolderProfileView extends Fragment implements AdapterView.OnItemSelectedListener{

    private static CharSequence[] UserOption;


    private RecyclerView folderProfileRV;
    private ProfileListAdapter folderProfileAdapt;
    private RecyclerView.LayoutManager foldProfLayMan;
    private Spinner cipherSpn;
    private Spinner userSpn;

    private View view;
    private int encryptValSelected = 1024;

    private Button genKeyButton;
    private Button addCommButton;
    private Button editButton;
    private Button removeButton;
    private Button delProfileButton;
    private Button saveProfileButton;

    private EditText rsaViewET;
    private EditText commandViewET;
    private EditText passwordET;

    private int PROFILE_POSITION = 0;
    private ArrayList<String> dataArrList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.secure_folder, container, false);

        // converting the userArray to a charSequence to put inside the spinner
        UserOption = userArray.toArray(new CharSequence[userArray.size()]);

        // initialize buttons
        genKeyButton = (Button) view.findViewById(R.id.rsa_generate_btn);
        addCommButton = (Button) view.findViewById(R.id.add_command_btn);
        editButton = (Button) view.findViewById(R.id.edit_list_btn);
        removeButton = (Button) view.findViewById(R.id.remove_list_btn);
        delProfileButton = (Button) view.findViewById(R.id.delete_profile_btn);
        saveProfileButton = (Button) view.findViewById(R.id.save_profile_btn);

        // initialize Edit Texts
        rsaViewET = (EditText) view.findViewById(R.id.rsa_display_view);
        commandViewET = (EditText) view.findViewById(R.id.add_command_et);
        passwordET = (EditText) view.findViewById(R.id.enter_password);

        // Recycler Views
        folderProfileRV = (RecyclerView) view.findViewById(R.id.profile_command_rv);

        // Spinners
        cipherSpn = (Spinner) view.findViewById(R.id.complexity_lst_spn);
        userSpn = (Spinner) view.findViewById(R.id.user_list);

        initiateUserSpinner();
        initiateCipherSpinner();

        cipherSpn.setOnItemSelectedListener(this);
        userSpn.setOnItemSelectedListener(this);

        // set Key listeners
        keyOnClickListener(genKeyButton, R.id.rsa_generate_btn);

        keyOnClickListener(addCommButton, R.id.add_command_btn);

        keyOnClickListener(removeButton, R.id.remove_list_btn);

        keyOnClickListener(saveProfileButton, R.id.save_profile_btn);

        return view;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch(parent.getId()) {

            case R.id.complexity_lst_spn:
                if (position != 0) {
                    encryptValSelected =
                            Integer.valueOf(String.valueOf(parent.getItemAtPosition(position)));
                }

                break;

            case R.id.user_list:
                PROFILE_POSITION = position;
                Log.d("Profile Position: ", ""+position);
                rsaViewET.setText(keyAddArray.get(position));
                passwordET.setText(passAddArray.get(position));

                if(!commandArray.isEmpty()) {
                    commandArrList = new ArrayList<>();
                    commandArrList = convertStringToArray(commandArray.get(PROFILE_POSITION));
                    folderProfileAdapt.swap();
                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        switch(parent.getId()) {

            case R.id.complexity_lst_spn:
                Log.d("complex spinner: ", "Nothing selected");
                break;

            case R.id.user_list:
                Log.d("user spinner: ", "Nothing selected");
                break;
        }
    }

    private void keyOnClickListener(Button button, int r){

        switch(r){
            case R.id.rsa_generate_btn:
                button.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createKeyTest();
                    }
                });
                break;

            case R.id.add_command_btn:
                button.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String command = commandViewET.getText().toString();
                        Log.d("command View Test:", " " +command);
                        commandArrList.add(command);
                        folderProfileAdapt.swap();
                    }
                });
                break;

            case R.id.remove_list_btn:
                button.setOnClickListener((new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(commandListPosition != -1) {
                            commandArrList.remove(commandListPosition);
                            folderProfileAdapt.swap();
                        }
                    }
                }));
                break;

            case R.id.save_profile_btn:

                button.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        keyAddArray.set(PROFILE_POSITION, rsaViewET.getText().toString());
                        passAddArray.set(PROFILE_POSITION, passwordET.getText().toString());

                        profilesDB.updateData(
                                userAddArray.get(PROFILE_POSITION), "SSH",
                                keyAddArray.get(PROFILE_POSITION),
                                passAddArray.get(PROFILE_POSITION),
                                convertArrayToString(commandArrList)
                        );

                        Toast.makeText(getActivity(),
                                ""+userArray.get(PROFILE_POSITION)+" profile saved",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.delete_profile_btn:
                break;
        }
    }
    private void initiateFolderListRV(){
        foldProfLayMan = new LinearLayoutManager(getContext());
        folderProfileAdapt = new ProfileListAdapter();
        folderProfileRV.setLayoutManager(foldProfLayMan);
        folderProfileRV.setAdapter(folderProfileAdapt);
    }

    private void initiateCipherSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.complexity_list_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cipherSpn.setAdapter(adapter);
        initiateFolderListRV();
    }

    private void initiateUserSpinner(){
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence> (
                getContext(), android.R.layout.simple_spinner_item, UserOption);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears

        userSpn.setAdapter(adapter); // Apply the adapter to the spinner
        userSpn.setOnItemSelectedListener(this);
    }

    private void createKeyTest() {
        String sshRsa;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(encryptValSelected);
            KeyPair keyPair = kpg.genKeyPair();
            byte[] pri = keyPair.getPrivate().getEncoded();
            byte[] pub = keyPair.getPublic().getEncoded();


            String privateKey = keyPair.getPrivate().toString();
            String publicKey = keyPair.getPublic().toString();

            sshRsa = "ssh-rsa " +privateKey.substring(32);

            if (sshRsa.contains(",")) {
                String[] parts = sshRsa.split(",");
                sshRsa = parts[0] +" root@android"; // 004
            } else {
                throw new IllegalArgumentException("String " + sshRsa + " does not contain -");
            }
            rsaViewET.setText(sshRsa);

        } catch (NoSuchAlgorithmException ex) {
            Log.e("SSHKeyManager", ex.toString());
        }
    }

    private String convertArrayToString(ArrayList arrList){
        String Result = "";
        for(int i = 0 ; i < arrList.size(); i++) {
            Result += String.valueOf(arrList.get(i)) + ";";
        }

        Log.d("convert arr to str : ","" +Result);

        return Result;
    }

    private ArrayList<String> convertStringToArray(String string){
        ArrayList<String> Result = new ArrayList<>();
        String[] parts = string.split(";");

        for(int i = 0; i < parts.length ; i++) {
            Result.add(parts[i]);
        }

        return Result;
    }

}
