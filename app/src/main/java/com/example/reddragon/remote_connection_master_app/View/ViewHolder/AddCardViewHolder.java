package com.example.reddragon.remote_connection_master_app.View.ViewHolder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.reddragon.remote_connection_master_app.MainActivity;
import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;


import static com.example.reddragon.remote_connection_master_app.MainActivity.Connect_Count;
import static com.example.reddragon.remote_connection_master_app.MainActivity.ipAddArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.portAddArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.preConDatabase;
import static com.example.reddragon.remote_connection_master_app.View.FrameFragments.RecyclerClass.recyclerClassAdapter;


/**
 * Created by RedDragon on 3/6/17.
 */

public class AddCardViewHolder extends RecyclerView.ViewHolder {

    MainActivity mainActivity = new MainActivity();
    ImageButton addNewCard;
    ImageButton saveDataBtn;
    ImageButton addUserBtn;
    private PopupWindow pwindo;

    public AddCardViewHolder(View itemView) {
        super(itemView);

        addNewCard = (ImageButton) itemView.findViewById(R.id.add_hostname_btn);
        saveDataBtn = (ImageButton) itemView.findViewById(R.id.save_host_icon);
        addUserBtn = (ImageButton) itemView.findViewById(R.id.add_prof_btn);

        addNewCard.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                ipAddArray.add("");
                portAddArray.add("");
                Connect_Count++;
                recyclerClassAdapter.notifyDataSetChanged();
                recyclerClassAdapter.notifyItemChanged(Connect_Count-1);

            }
        });

        saveDataBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 0;
                while(x < (ipAddArray.size())){
                    preConDatabase.insertData(ipAddArray.get(x), portAddArray.get(x));
                    x++;
                }
            }
        });

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initiatePopupWindow();

            }
        });
    }

    private void initiatePopupWindow() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mainActivity.getMainContext());
        alertDialog.setTitle("PASSWORD");
        alertDialog.setMessage("Enter Password");

        final EditText input = new EditText(mainActivity.getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.add_person);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String password = input.getText().toString();
                        if (password.compareTo("") == 0) {
                                Toast.makeText(mainActivity.getApplicationContext(),
                                        "something works", Toast.LENGTH_SHORT);
                            }
                        }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

}


