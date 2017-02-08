package com.example.reddragon.remote_connection_master_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.reddragon.remote_connection_master_app.View.ConSettingsView;
import com.example.reddragon.remote_connection_master_app.View.ConsoleView;

/**------------------------------------------------------------------------------------------------->
 * Plan: Everything is written around the android UI thread as the central core of the application /
 *                                                                                                 \
 *                                                                    written: by,                 /
 *                                                                                 Rafat Khandaker \
 *                                                                                                 /
 *                                                                                        02/06/17 \
 * ----------------------------------------------------------------------------------------------**/

public class MainActivity extends FragmentActivity {

private LinearLayout connectionSettings;
private LinearLayout console;
private ImageButton settingsButton;
private static RecyclerView previousConnectRecycler;

private static final Fragment CONNECTION_SETTINGS = new ConSettingsView();
private static final ConsoleView CONSOLE = new ConsoleView();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsButton = (ImageButton)findViewById(R.id.settings_button);
        previousConnectRecycler = (RecyclerView)findViewById(R.id.main_recycler);

        openOnClickSettings(settingsButton);

        connectionSettings = new LinearLayout(this);
        console = new LinearLayout(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.settings_menu:
                launchContainer(CONNECTION_SETTINGS);
                return true;

            case R.id.console_menu:
                launchContainer(CONSOLE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();
    }

    private void openOnClickSettings(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptionsMenu();
            }
        });
    }

    private void initiateRecycler(RecyclerView recycler){

    }

    private void launchContainer(Fragment fragment){
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.add(R.id.mainframe_container, fragment);
        fragTrans.commit();
    }


}
