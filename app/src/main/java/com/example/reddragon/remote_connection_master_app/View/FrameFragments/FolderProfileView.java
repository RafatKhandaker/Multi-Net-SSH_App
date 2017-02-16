package com.example.reddragon.remote_connection_master_app.View.FrameFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.ConsoleListAdapter;

/**
 * Created by RedDragon on 2/7/17.
 */

public class FolderProfileView extends Fragment {

    private RecyclerView folderProfileRV;
    private ConsoleListAdapter folderProfileAdapt;
    private RecyclerView.LayoutManager foldProfLayMan;

    private Button genKeyButton;
    private Button addCommButton;
    private Button editButton;
    private Button removeButton;
    private Button delProfileButton;
    private Button saveProfileButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.secure_folder, container, false);

        genKeyButton = (Button) view.findViewById(R.id.rsa_generate_btn);
        addCommButton = (Button) view.findViewById(R.id.add_command_btn);
        editButton = (Button) view.findViewById(R.id.edit_list_btn);
        removeButton = (Button) view.findViewById(R.id.remove_list_btn);
        delProfileButton = (Button) view.findViewById(R.id.delete_profile_btn);
        saveProfileButton = (Button) view.findViewById(R.id.save_profile_btn);

        folderProfileRV = (RecyclerView) view.findViewById(R.id.profile_command_rv);

        initiateFolderListRV();

        return view;

    }

    private void initiateFolderListRV(){
        foldProfLayMan = new LinearLayoutManager(getContext());
        folderProfileAdapt = new ConsoleListAdapter();
        folderProfileRV.setLayoutManager(foldProfLayMan);
        folderProfileRV.setAdapter(folderProfileAdapt);
    }
}
