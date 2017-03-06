package com.example.reddragon.remote_connection_master_app.View.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;

import java.util.ArrayList;

import static com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter.dataSize;

/**
 * Created by RedDragon on 3/6/17.
 */

public class AddCardViewHolder extends RecyclerView.ViewHolder {

    CardView addNewCard;
    public static ArrayList<String> changeData = new ArrayList<>();

    public AddCardViewHolder(View itemView) {
        super(itemView);


        final MainContainerAdapter adapter = new MainContainerAdapter();

        addNewCard = (CardView) itemView.findViewById(R.id.add_card_view);

        addNewCard.setOnTouchListener(new View.OnTouchListener() {
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
