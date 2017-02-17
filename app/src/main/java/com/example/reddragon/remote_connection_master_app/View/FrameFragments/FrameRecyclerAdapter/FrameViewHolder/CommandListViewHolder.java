package com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.FrameViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.reddragon.remote_connection_master_app.R;

/**
 * Created by RedDragon on 2/16/17.
 */

public class CommandListViewHolder extends RecyclerView.ViewHolder {

    private EditText commandListText;

    public CommandListViewHolder(View itemView) {
        super(itemView);
        commandListText = (EditText) itemView.findViewById(R.id.conn_name_txt);
    }

    public void onBind(int position){
         commandListText.setText(commandListText.getText());

    }
}
