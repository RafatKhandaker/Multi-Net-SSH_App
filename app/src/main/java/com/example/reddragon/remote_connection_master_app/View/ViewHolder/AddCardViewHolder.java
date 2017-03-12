package com.example.reddragon.remote_connection_master_app.View.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;

import java.util.ArrayList;


/**
 * Created by RedDragon on 3/6/17.
 */

public class AddCardViewHolder extends RecyclerView.ViewHolder {

    MainContainerAdapter adapter;
    ImageButton addNewCard;
    public static ArrayList<String> changeData = new ArrayList<>();

    public AddCardViewHolder(View itemView) {
        super(itemView);

        adapter = new MainContainerAdapter();

        addNewCard = (ImageButton) itemView.findViewById(R.id.add_hostname_btn);

        addNewCard.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("onTouch pass: ", " pass add Card touch " );
                adapter.updateData();
            }
        });
    }

}
