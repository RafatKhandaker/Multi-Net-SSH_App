package com.example.reddragon.remote_connection_master_app.View.FrameFragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;


/**
 * Created by RedDragon on 2/11/17.
 */

public class RecyclerClass extends Fragment {

    private  RecyclerView preConnectRecycler;
    private  RecyclerView.Adapter recyclerAdapter;
    private  RecyclerView.LayoutManager recyclerLayoutManager;

    public RecyclerClass(){}

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_connect, container, false);

        // initiate recycler
        preConnectRecycler = (RecyclerView) view.findViewById(R.id.main_recycler);
        recyclerLayoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new MainContainerAdapter();
        preConnectRecycler.setLayoutManager(recyclerLayoutManager);
        preConnectRecycler.setAdapter(recyclerAdapter);

        return view;
    }

    public RecyclerView.Adapter getRecyclerAdapter(){ return recyclerAdapter; }


}
