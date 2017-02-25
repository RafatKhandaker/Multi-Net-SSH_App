package com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.FrameViewHolder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.reddragon.remote_connection_master_app.R;

import static com.example.reddragon.remote_connection_master_app.MainActivity.commandArrList;

/**
 * Created by RedDragon on 2/16/17.
 */

public class CommandListViewHolder extends RecyclerView.ViewHolder {

    private RelativeLayout connectListLayout;
    private TextView commandListText;

    private static int commandListPosition = -1;

    public CommandListViewHolder(View itemView) {
        super(itemView);

        connectListLayout = (RelativeLayout) itemView.findViewById(R.id.connect_list_layout);
        commandListText = (TextView) itemView.findViewById(R.id.conn_name_txt);

        connectListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    commandListPosition = getAdapterPosition();
                    v.setBackgroundColor(Color.YELLOW);
                    commandListText.setTextColor(Color.BLACK);
            }
        });

    }

    public void onBind(int position){

        if(commandArrList.size() == 0){ commandListText.setText("+ New Command"); }

        else if(position < commandArrList.size()) {
            commandListText.setText(commandArrList.get(position));
        }

    }

    public static int getCommandListPosition(){ return commandListPosition; }

}
