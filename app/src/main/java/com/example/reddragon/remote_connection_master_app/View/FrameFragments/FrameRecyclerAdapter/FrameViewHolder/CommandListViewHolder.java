package com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.FrameViewHolder;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.SQLiteDB.StoreCommandsDB;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FolderProfileView;

import java.util.ArrayList;

import static com.example.reddragon.remote_connection_master_app.MainActivity.commandListDB;

/**
 * Created by RedDragon on 2/16/17.
 */

public class CommandListViewHolder extends RecyclerView.ViewHolder {

    private EditText commandListText;
    private Cursor commViewRes = commandListDB.getAllData();
//    private StringBuffer bufferValue;
    private Fragment folderProfileView;
    private ArrayList<String> nameArrList = new ArrayList<>();
    private ArrayList<String> commArrList = new ArrayList<>();


    public CommandListViewHolder(View itemView) {
        super(itemView);
        folderProfileView = new FolderProfileView();
        commandListText = (EditText) itemView.findViewById(R.id.conn_name_txt);

    }

    public void onBind(int position){
//        if(res.getCount() == 0){ commandListText.setText(commandListText.getText()); }
//        else{
        if(commViewRes.getCount() == 0){
            commandListText.setText("+ New Command");
        }else if(commViewRes == null){
            commandListText.setText("+ New Command");
        }
        else {
            commandListDB = new StoreCommandsDB(folderProfileView.getContext());
            loadSavedCommandData();
            commandListText.setText(nameArrList.get(position));
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
