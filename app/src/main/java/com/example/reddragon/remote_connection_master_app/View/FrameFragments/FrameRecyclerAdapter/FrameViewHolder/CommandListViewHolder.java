package com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.FrameViewHolder;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FolderProfileView;

import java.util.ArrayList;

import static com.example.reddragon.remote_connection_master_app.MainActivity.commandListDB;

/**
 * Created by RedDragon on 2/16/17.
 */

public class CommandListViewHolder extends RecyclerView.ViewHolder {

    private TextView commandListText;
    private Cursor commViewRes;
//    private StringBuffer bufferValue;
    private Fragment folderProfileView;
    private ArrayList<String> nameArrList = new ArrayList<>();
    private ArrayList<String> commArrList = new ArrayList<>();


    public CommandListViewHolder(View itemView) {
        super(itemView);
        folderProfileView = new FolderProfileView();
        commandListText = (TextView) itemView.findViewById(R.id.conn_name_txt);
        commViewRes = commandListDB.getAllData();

        loadSavedCommandData();


    }

    public void onBind(int position){

        if(commViewRes.getCount() == 0){ commandListText.setText("+ New Command"); }

        else if(position < commArrList.size()) {
            commandListText.setText(commArrList.get(position));
        }

    }

    private void loadSavedCommandData(){
        
        if(commViewRes.getCount() != 0) {
//            bufferValue = new StringBuffer();
            while (commViewRes.moveToNext()) {
                commArrList.add(commViewRes.getString(1));
                nameArrList.add(commViewRes.getString(2));
            }
        }
    }


}
