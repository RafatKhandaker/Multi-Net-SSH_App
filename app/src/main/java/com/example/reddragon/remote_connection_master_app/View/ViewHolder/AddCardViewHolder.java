package com.example.reddragon.remote_connection_master_app.View.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;

import java.util.ArrayList;

import static com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter.dataSize;

/**
 * Created by RedDragon on 3/6/17.
 */

public class AddCardViewHolder extends RecyclerView.ViewHolder {

    ImageButton addNewCard;
    public static ArrayList<String> changeData = new ArrayList<>();

    public AddCardViewHolder(View itemView) {
        super(itemView);


        final MainContainerAdapter adapter = new MainContainerAdapter();

        addNewCard = (ImageButton) itemView.findViewById(R.id.add_hostname_btn);

        addNewCard.setOnTouchListener(new ImageButton.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.d("onTouch pass: ", " pass add Card touch " );

                dataSize++;
                adapter.updateData();

                return false;
            }
        });
    }
}
