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

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.ConsoleListAdapter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Created by RedDragon on 2/7/17.
 */

public class FolderProfileView extends Fragment implements AdapterView.OnItemSelectedListener{

    private RecyclerView folderProfileRV;
    private ConsoleListAdapter folderProfileAdapt;
    private RecyclerView.LayoutManager foldProfLayMan;
    private Spinner spinner;

    private int encryptValSelected = 0;

    private Button genKeyButton;
    private Button addCommButton;
    private Button editButton;
    private Button removeButton;
    private Button delProfileButton;
    private Button saveProfileButton;

    private EditText rsaViewET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.secure_folder, container, false);

        genKeyButton = (Button) view.findViewById(R.id.rsa_generate_btn);
        addCommButton = (Button) view.findViewById(R.id.add_command_btn);
        editButton = (Button) view.findViewById(R.id.edit_list_btn);
        removeButton = (Button) view.findViewById(R.id.remove_list_btn);
        delProfileButton = (Button) view.findViewById(R.id.delete_profile_btn);
        saveProfileButton = (Button) view.findViewById(R.id.save_profile_btn);

        rsaViewET = (EditText) view.findViewById(R.id.rsa_display_view);

        folderProfileRV = (RecyclerView) view.findViewById(R.id.profile_command_rv);

        spinner = (Spinner) view.findViewById(R.id.complexity_lst_spn);



// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.complexity_list_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        initiateFolderListRV();
//        createKeyTest();

        spinner.setOnItemSelectedListener(this);

        keyOnClickListener(genKeyButton, R.id.rsa_generate_btn);

        return view;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        encryptValSelected = Integer.valueOf(String.valueOf(parent.getItemAtPosition(position)));

        Log.d("encrypt test: ", "" +encryptValSelected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback

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
        }
    }
    private void initiateFolderListRV() {
        foldProfLayMan = new LinearLayoutManager(getContext());
        folderProfileAdapt = new ConsoleListAdapter();
        folderProfileRV.setLayoutManager(foldProfLayMan);
        folderProfileRV.setAdapter(folderProfileAdapt);
    }

    private void createKeyTest() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(encryptValSelected);
            KeyPair keyPair = kpg.genKeyPair();
            byte[] pri = keyPair.getPrivate().getEncoded();
            byte[] pub = keyPair.getPublic().getEncoded();

//            String privateKey = new String(pri);
//            String publicKey = new String(pub);


            String privateKey = keyPair.getPrivate().toString();
            String publicKey = keyPair.getPublic().toString();

            rsaViewET.setText(privateKey);

//            Log.d("SSHKeyManager", "Private Key: " +privateKey);
//            Log.d("SSHLeyManager", "Public Key: " +publicKey);
        } catch (NoSuchAlgorithmException ex) {
            Log.e("SSHKeyManager", ex.toString());
        }
    }

}
