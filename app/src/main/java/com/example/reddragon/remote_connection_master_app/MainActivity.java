package com.example.reddragon.remote_connection_master_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.LinearLayout;

/** written by Rafat Khandaker
 *                                          02/06/17
 * Plan : everything is written around the android UI thread as the central core of the application
 *
 *
 * **/

public class MainActivity extends AppCompatActivity {

LinearLayout connectionSettings;
LinearLayout console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionSettings = new LinearLayout(this);
        console = new LinearLayout(this);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

}
