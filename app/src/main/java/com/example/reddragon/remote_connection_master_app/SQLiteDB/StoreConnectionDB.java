package com.example.reddragon.remote_connection_master_app.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RedDragon on 2/7/17.
 */

public class StoreConnectionDB extends SQLiteOpenHelper {

        private SQLiteDatabase sqLiteDatabase;
        private ContentValues contentValues;

        private static final String DATABASE_NAME = "Stored_Connections.db";
        private static final String TABLE_NAME = "connecton_table";

        private static final String COL_1 = "ID";
        private static final String COL_2 = "IP";
        private static final String COL_3 = "USERNAME";
        private static final String COL_4 = "TYPE";
        private static final String COL_5 = "PORT";
        private static final String COL_6 = "KEY";
        private static final String COL_7 = "PASSWORD";

        private static final int DATABASE_VERSION = 1;


    public StoreConnectionDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " +TABLE_NAME+ " (ID INTEGER" +
                ",IP TEXT, NAME TEXT, TYPE TEXT, PORT INTEGER, KEY TEXT, PASSWORD TEXT, USERNAME TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData( String ip, String username, String type, String port, String key,
                               String password){

        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(COL_2, ip);
        contentValues.put(COL_3, username);
        contentValues.put(COL_4, type);
        contentValues.put(COL_5, port);
        contentValues.put(COL_6, key);
        contentValues.put(COL_7, password);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if(result == -1){ return false; } else{ return true; }
    }

    public boolean insertData(String id, String ip, String port){

        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, ip);
        contentValues.put(COL_5, port);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if(result == -1){ return false; } else { return true; }
    }

    public Cursor getAllData() {
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null); }



    public boolean updateData(String id, String ip, String username, String type, String port, String key,
                              String password){

        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, ip);
        contentValues.put(COL_3, username);
        contentValues.put(COL_4, type);
        contentValues.put(COL_5, port);
        contentValues.put(COL_6, key);
        contentValues.put(COL_7, password);

        sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{ id } );

        return true;
    }

    public void deleteData(String id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[]{id});

//        sqLiteDatabase.execSQL(
//                String.format("DELETE FROM %s WHERE IP = %s (SELECT IP FROM %s WHERE PORT = %s",
//                TABLE_NAME,ip,ip,port));

    }

}
