package com.example.reddragon.remote_connection_master_app;

import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.reddragon.remote_connection_master_app.SQLiteDB.StoreConnectionDB;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.ConSettingsView;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.ConsoleView;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.RecyclerClass;

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
private ImageView settingsButton;
private ImageView profileImageButton;

// setting up Immutable objects for thread handling

private static DrawerLayout drawerLayout;
private static StoreConnectionDB preConDatabase;

private static final Fragment CONNECTION_SETTINGS = new ConSettingsView();
private static final ConsoleView CONSOLE = new ConsoleView();

private android.support.v7.widget.RecyclerView preConnectRecycler;
private android.support.v7.widget.RecyclerView.Adapter recyclerAdapter;
private android.support.v7.widget.RecyclerView.LayoutManager recyclerLayoutManager;

private RecyclerClass recyclerClass = new RecyclerClass();

    private Cursor res;
    private StringBuffer bufferValue;

    private static FragmentManager fragMan;


    private android.support.v4.app.ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;
    String[] userListArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**  not complete, needs better thread handling.. eventually will overload UI thread**/

        // create Settings button
        settingsButton = (ImageView)findViewById(R.id.settings_button);
        connectionSettings = new LinearLayout(this);
        console = new LinearLayout(this);

        // instantiate database
        preConDatabase = new StoreConnectionDB(this);

        // initiate recycler  * comment this section to test the sliding drawer
        launchContainer(recyclerClass);

        // initiate menu
        initiateSettingsClick(settingsButton);

        //initiate profiles
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList = (ListView)findViewById(R.id.drawerList);
        profileImageButton = (ImageView)findViewById(R.id.profile_button);

        initiateSlidingDrawer();
        initiateTrayClickListener(profileImageButton, R.id.profile_button);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.mainframe_container).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private void loadSavedData(){
        res = preConDatabase.getAllData();
        if(res.getCount() != 0) {
            bufferValue = new StringBuffer();
            while (res.moveToNext()) {
                // load the data individual from SQLite
            }
        }
    }


    private void launchContainer(Fragment fragment){
        fragMan = getSupportFragmentManager();
        fragMan.beginTransaction()
                .replace(R.id.mainframe_container, fragment)
                .commit();
    }

    private void initiateSettingsClick(View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, settingsButton);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu, popup.getMenu());
                popup.show();
            }
        });
    }

    private void initiateTrayClickListener(View v, int r){
        switch(r){
            case R.id.profile_button:
              v.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if(!drawerLayout.isDrawerOpen(drawerList)){
                          drawerLayout.openDrawer(drawerList);
                      }else if(drawerLayout.isDrawerOpen(drawerList)){
                          drawerLayout.closeDrawer(drawerList);
                      }
                  }
              });
        }
    }

    private void initiateSlidingDrawer(){
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        userListArray = new String[] { "root", "user1", "user2", "user3" };

        drawerList.setAdapter(new ArrayAdapter<>(MainActivity.this,
                R.layout.drawer_list_item, userListArray));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        initiateDrawerToggle();
    }

    private void initiateDrawerToggle(){
        drawerToggle = new android.support.v4.app.ActionBarDrawerToggle(
                this, drawerLayout, R.drawable.profile_icon, R.string.drawer_open, R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem();
        }

        private void selectItem(){}
    }

}
