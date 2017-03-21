package com.example.reddragon.remote_connection_master_app.View.FrameFragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;
import com.example.reddragon.remote_connection_master_app.View.ViewHolder.CardViewHolder;

import static com.example.reddragon.remote_connection_master_app.MainActivity.Connect_Count;
import static com.example.reddragon.remote_connection_master_app.MainActivity.ipAddArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.portAddArray;

/**
 * Created by RedDragon on 2/11/17.
 */

public class RecyclerClass extends Fragment {

    private  RecyclerView preConnectRecycler;
    public static RecyclerView.Adapter recyclerClassAdapter;
    private  RecyclerView.LayoutManager recyclerLayoutManager;

    private CardViewHolder cardViewHolder;

    public RecyclerClass(){}

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_connect, container, false);

        cardViewHolder = new CardViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.main_card_holder, null));

        // initiate recycler
        preConnectRecycler = (RecyclerView) view.findViewById(R.id.main_recycler);
        recyclerLayoutManager = new LinearLayoutManager(getContext());
        recyclerClassAdapter = new MainContainerAdapter();
        preConnectRecycler.setLayoutManager(recyclerLayoutManager);
        preConnectRecycler.setAdapter(recyclerClassAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback
                (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                //Remove swiped item from list and notify the RecyclerView
                if(Connect_Count > 0) {
                    ipAddArray.remove(cardViewHolder.getAdapterPosition() +1);
                    portAddArray.remove(cardViewHolder.getAdapterPosition() +1);
                    Connect_Count--;

                    recyclerClassAdapter.notifyDataSetChanged();
                }

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(preConnectRecycler);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
