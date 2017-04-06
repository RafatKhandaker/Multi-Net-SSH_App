package com.example.reddragon.remote_connection_master_app.Controller;


import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.PopupMenu;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.reddragon.remote_connection_master_app.Model.SQLiteDB.StoreCommandsDB;
import com.example.reddragon.remote_connection_master_app.Model.SQLiteDB.StoreConnectionDB;
import com.example.reddragon.remote_connection_master_app.Model.SQLiteDB.StoreProfilesDB;
import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.ConsoleView;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FolderProfileView;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.RecyclerClass;

import java.util.ArrayList;


/**------------------------------------------------------------------------------------------------->
 * Plan: Everything is written around the android UI thread as the central core of the application /
 *                                                                                                 \
 *                                                                    written: by,                 /
 *                                                                                 Rafat Khandaker \
 *                                                                                                 /
 *                                                                                        02/06/17 \
 * ----------------------------------------------------------------------------------------------**/

public class MainActivity extends FragmentActivity {

private ImageView settingsButton;
private ImageView profileImageButton;
private ImageView consoleButton;
private ImageView folderLockButton;

//-----------------setting up Immutable objects for possible thread handling------------------------

private static DrawerLayout drawerLayout;

public static StoreConnectionDB preConDatabase;
public static StoreCommandsDB commandListDB;
public static StoreProfilesDB profilesDB;

public static ArrayList<String> commandArrList;

public static ArrayList<String>
        ipAddArray, portAddArray, userAddArray, typeAddArray, keyAddArray, passAddArray;

    public static ArrayList<Integer> storeRemovedIDData = new ArrayList<>();

    //---------------------Connection Variables from SQLite----------------------------

public static ArrayList<String>
           idArray, ipArray, userArray, typeArray, portArray, keyArray, passArray;
//--------------------------------------------------------------------------------------------------

public static ArrayAdapter drawerArrayAdapt;

private static final Fragment CONNECTION_SETTINGS = new FolderProfileView();
private static final ConsoleView CONSOLE = new ConsoleView();

public static int Connect_Count;
private static int CHECK_FRAGMENT = 0;
private static final int SSH_FRAG = 1, CONSOLE_FRAG = 2;


private RecyclerClass recyclerClass = new RecyclerClass();
private static FragmentManager fragMan;

    private android.support.v4.app.ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    /**  not complete, needs better thread handling.. eventually will overload UI thread **/

        initializeArrayData();

        // create tray button
        settingsButton = (ImageView)findViewById(R.id.settings_button);
        consoleButton = (ImageView)findViewById(R.id.ssh_button);
        folderLockButton = (ImageView)findViewById(R.id.folder_button);

        // instantiate database
        preConDatabase = new StoreConnectionDB(this);
        commandListDB = new StoreCommandsDB(this);
        profilesDB = new StoreProfilesDB(this);

        // initiate recycler  * comment this section to test the sliding drawer

        checkOnRestoreContainer();

        // initiate menu
        initiateSettingsClick(settingsButton);

        //initiate slider on profile button
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList = (ListView)findViewById(R.id.drawerList);
        profileImageButton = (ImageView)findViewById(R.id.profile_button);

        initiateSlidingDrawer();

        initiateTrayClickListener(profileImageButton, R.id.profile_button);
        initiateTrayClickListener(consoleButton, R.id.ssh_button);
        initiateTrayClickListener(folderLockButton, R.id.folder_button);

        // load Stored SQLite Data
        loadSavedConnectionData();
        loadSavedCommandData(commandArrList);
        loadSavedProfileData();

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.mainframe_container).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("ip_add_array", ipAddArray);
        outState.putStringArrayList("port_add_array", portAddArray);
        outState.putInt("count_connect", Connect_Count);
        outState.putInt("check_fragment", CHECK_FRAGMENT);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ipAddArray = savedInstanceState.getStringArrayList("ip_add_array");
        portAddArray = savedInstanceState.getStringArrayList("port_add_array");
        Connect_Count = savedInstanceState.getInt("count_connect");
        CHECK_FRAGMENT = savedInstanceState.getInt("check_fragment");
    }

    @Override
    public void onBackPressed() {
        launchContainer(recyclerClass);
        CHECK_FRAGMENT = 0;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void initializeArrayData(){

        idArray = new ArrayList<>();
        ipArray = new ArrayList<>();
        userArray = new ArrayList<>();
        typeArray = new ArrayList<>();
        portArray = new ArrayList<>();
        keyArray = new ArrayList<>();
        passArray = new ArrayList<>();

        commandArrList = new ArrayList<>();

        ipAddArray = new ArrayList<>();
        portAddArray = new ArrayList<>();
        userAddArray = new ArrayList<>();
        keyAddArray = new ArrayList<>();
        passAddArray = new ArrayList<>();

    }

    private void checkOnRestoreContainer(){
        switch(CHECK_FRAGMENT) {

            case SSH_FRAG:
                launchContainer(CONNECTION_SETTINGS);
                break;
            case CONSOLE_FRAG:
                launchContainer(CONSOLE);
                break;
            default:
                launchContainer(recyclerClass);
        }
    }

    private void loadSavedProfileData(){
        Cursor res = profilesDB.getAllData();

        if(res.getCount() != 0){
            if(res.getCount() != 0 ){
                while( res.moveToNext()){
                    userArray.add(res.getString(0));
                    typeArray.add(res.getString(1));
                    keyArray.add(res.getString(2));
                    passArray.add(res.getString(3));
                }
                if(!userArray.isEmpty()){ userAddArray = userArray; }
                if(!typeArray.isEmpty()){ typeAddArray = typeArray; }
                if(!keyArray.isEmpty()){ keyAddArray = keyArray; }
                if(!passArray.isEmpty()){ passAddArray = passArray; }

                Log.d("Test profile data: ",
                        "" +userArray.get(0) +
                        " " +typeArray.get(0) +
                        " " +keyArray.get(0) +
                        " " +passArray.get(0));

            }
        }
    }
    private void loadSavedConnectionData(){

        Cursor res = preConDatabase.getAllData();
        if(res.getCount() != 0) {
            while (res.moveToNext()) {

                // load the data individual from SQLite]
                idArray.add(res.getString(0));
                ipArray.add(res.getString(1));
                portArray.add(res.getString(2));

            }
        }

        if(ipArray.size() > 0 && ipAddArray.size() < ipArray.size()){

            ipAddArray = ipArray;
            portAddArray = portArray;
            Connect_Count = ipAddArray.size();
        }

    }


    public static ArrayList<String> loadSavedCommandData(ArrayList<String> arrList){
        Cursor res = commandListDB.getAllData();
        if(res.getCount() != 0) {
            while (res.moveToNext()) {
                arrList.add(res.getString(1));
            }
        }
        return arrList;
    }

//-----------------------------Main Control Methods-------------------------------------------------

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
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()){
                            case R.id.main_menu:
                                launchContainer(recyclerClass);
                                CHECK_FRAGMENT = 0;
                                return true;

                            case R.id.settings_menu:
                                launchContainer(CONNECTION_SETTINGS);
                                CHECK_FRAGMENT = SSH_FRAG;
                                return true;

                            case R.id.console_menu:
                                launchContainer(CONSOLE);
                                CHECK_FRAGMENT = CONSOLE_FRAG;
                                return true;

                            case R.id.attack_menu:  // incomplete idea for now
                                return true;
                        }
                        return false;
                    }
                });
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
                          drawerLayout.closeDrawer(drawerList);}
                  }
              });
                break;
            case R.id.folder_button:
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchContainer(CONNECTION_SETTINGS);
                        CHECK_FRAGMENT = SSH_FRAG;
                    }
                });
                break;
            case R.id.ssh_button:
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchContainer(CONSOLE);
                        CHECK_FRAGMENT = CONSOLE_FRAG;
                    }
                });
                break;
        }

    }

//---------------------------Drawer Methods---------------------------------------------------------

    private void initiateSlidingDrawer(){
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        drawerArrayAdapt = new ArrayAdapter<>(this, R.layout.drawer_list_item, userArray);

        drawerList.setAdapter(drawerArrayAdapt);

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
            selectItem(position);
        }

        private void selectItem(int position){
            switch(position){
                case 0:
                    Log.d("on pass sel item draw: ","" +position);
                    break;
                case 1:
                    Log.d("on pass sel item draw: ","" +position);
                    break;
                default:
                    break;
            }
        }
    }
}
