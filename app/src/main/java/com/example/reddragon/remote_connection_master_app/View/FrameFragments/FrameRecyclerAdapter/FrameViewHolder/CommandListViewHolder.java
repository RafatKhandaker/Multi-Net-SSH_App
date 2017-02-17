package com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.FrameViewHolder;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FolderProfileView;

/**
 * Created by RedDragon on 2/16/17.
 */

public class CommandListViewHolder extends RecyclerView.ViewHolder {

    private EditText commandListText;

//    private StringBuffer bufferValue;
    private Fragment folderProfileView;


    public CommandListViewHolder(View itemView) {
        super(itemView);
        folderProfileView = new FolderProfileView();
        commandListText = (EditText) itemView.findViewById(R.id.conn_name_txt);

    }

    public void onBind(int position){
//        if(res.getCount() == 0){ commandListText.setText(commandListText.getText()); }
//        else{
//            commandListText.setText(commandArrList.get(position));
//        }

    }


}
