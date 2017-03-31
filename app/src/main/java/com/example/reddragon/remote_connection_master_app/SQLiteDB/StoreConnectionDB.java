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
        private static final String COL_3 = "PORT";

        private static final int DATABASE_VERSION = 1;


    public StoreConnectionDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " +TABLE_NAME+ " (ID TEXT,IP TEXT, PORT INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String id, String ip, String port){

        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, ip);
        contentValues.put(COL_3, port);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if(result == -1){ return false; } else { return true; }
    }

    public Cursor getAllData() {
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null); }



    public boolean updateData(String id, String ip, String port){

        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, ip);
        contentValues.put(COL_3, port);


        sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{ id } );

        return true;
    }

    public boolean updateData(String id, String newID){

        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(COL_1, newID);

        sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = "+id, new String[]{ newID } );

        return true;
    }

    public void deleteData(String id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

}
