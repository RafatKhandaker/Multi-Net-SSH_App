package com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.FrameViewHolder;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FolderProfileView;

import static com.example.reddragon.remote_connection_master_app.MainActivity.commandArrList;

/**
 * Created by RedDragon on 2/16/17.
 */

public class CommandListViewHolder extends RecyclerView.ViewHolder {

    private TextView commandListText;
//    private StringBuffer bufferValue;
    private Fragment folderProfileView;

    public CommandListViewHolder(View itemView) {
        super(itemView);
        folderProfileView = new FolderProfileView();
        commandListText = (TextView) itemView.findViewById(R.id.conn_name_txt);



    }

    public void onBind(int position){

        if(commandArrList.size() == 0){ commandListText.setText("+ New Command"); }

        else if(position < commandArrList.size()) {
            commandListText.setText(commandArrList.get(position));
        }

    }

}
