package com.example.reddragon.remote_connection_master_app.View.FrameFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.ConsoleListAdapter;


/**
 * Created by RedDragon on 2/7/17.
 */

public class ConsoleView extends Fragment {

    private RecyclerView consoleListRV;
    private ConsoleListAdapter consoleListAdapter;
    private RecyclerView.LayoutManager conListLayMan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.console, container, false);

        consoleListRV = (RecyclerView) view.findViewById(R.id.command_list_rv);

        initiateConListRV();

        return view;
    }

    private void initiateConListRV(){
        conListLayMan = new LinearLayoutManager(getContext());
        consoleListAdapter = new ConsoleListAdapter();
        consoleListRV.setLayoutManager(conListLayMan);
        consoleListRV.setAdapter(consoleListAdapter);
    }
}
