package com.example.reddragon.remote_connection_master_app;

import android.content.ClipData;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.reddragon.remote_connection_master_app.SQLiteDB.StoreCommandsDB;
import com.example.reddragon.remote_connection_master_app.SQLiteDB.StoreConnectionDB;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.ConsoleView;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FolderProfileView;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.RecyclerClass;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;

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
private Button btnClosePopup;
// setting up Immutable objects for possible thread handling

private static DrawerLayout drawerLayout;
public static StoreConnectionDB preConDatabase;
public static StoreCommandsDB commandListDB;

public static ArrayList<String> commandArrList;

public static int Connect_Count;
public static ArrayList<String> ipAddArray = new ArrayList<>();
public static ArrayList<String> portAddArray = new ArrayList<>();

   // Connection Variables from SQLite
public static ArrayList<String>
           ipArray, userArray, typeArray, portArray, keyArray, passArray;

private static final Fragment CONNECTION_SETTINGS = new FolderProfileView();
private static final ConsoleView CONSOLE = new ConsoleView();

private static int CHECK_FRAGMENT = 0;

private static final int
    SSH_FRAG = 1, CONSOLE_FRAG = 2;


private RecyclerClass recyclerClass = new RecyclerClass();

private static FragmentManager fragMan;

    private PopupWindow popupWindow;

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            popupWindow.dismiss();

        }
    };

    private android.support.v4.app.ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;
    private ArrayList<String> userListArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    /**  not complete, needs better thread handling.. eventually will overload UI thread **/

        initializeArrayData();

        // create Settings button
        settingsButton = (ImageView)findViewById(R.id.settings_button);
        consoleButton = (ImageView)findViewById(R.id.ssh_button);
        folderLockButton = (ImageView)findViewById(R.id.folder_button);

        // instantiate database
        preConDatabase = new StoreConnectionDB(this);
        commandListDB = new StoreCommandsDB(this);

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

        ipArray = new ArrayList<>();
        userArray = new ArrayList<>();
        typeArray = new ArrayList<>();
        portArray = new ArrayList<>();
        keyArray = new ArrayList<>();
        passArray = new ArrayList<>();

        commandArrList = new ArrayList<>();

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

    private void loadSavedConnectionData(){
        userListArray.add("+ Add New Profile");

        Cursor res = preConDatabase.getAllData();
        if(res.getCount() != 0) {
            StringBuffer bufferValue = new StringBuffer();
            while (res.moveToNext()) {

                // load the data individual from SQLite]
                ipArray.add(res.getString(1));
                userArray.add(res.getString(2));
                typeArray.add(res.getString(3));
                portArray.add(res.getString(4));
                keyArray.add(res.getString(5));
                passArray.add(res.getString(6));
            }
        }
        if(ipArray.size() > 0 && ipAddArray.size() < ipArray.size()){
            ipAddArray = ipArray;
            portAddArray = portArray;
           Connect_Count = ipAddArray.size();
        }

        if(userArray.size() > 0){ userListArray = userArray; }
    }


    public static ArrayList<String> loadSavedCommandData(ArrayList<String> arrList){
        Cursor res = commandListDB.getAllData();
        if(res.getCount() != 0) {
//            bufferValue = new StringBuffer();
            while (res.moveToNext()) {
                arrList.add(res.getString(1));
            }
        }
        return arrList;
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
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()){
                            case R.id.main_menu:
                                Log.d("on menu click pass: ", "main activity");
                                launchContainer(recyclerClass);
                                return true;

                            case R.id.settings_menu:
                                launchContainer(CONNECTION_SETTINGS);
                                return true;

                            case R.id.console_menu:
                                launchContainer(CONSOLE);
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

    private void initiateSlidingDrawer(){
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
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
            selectItem(position);
        }

        private void selectItem(int position){
            if(userListArray.get(position).contains("+ Add New Profile")) {
                initiatePopupWindow();
            }
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

    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            popupWindow = new PopupWindow(layout, 300, 370, true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("fail Popup Menu", "main");
        }
    }

}
