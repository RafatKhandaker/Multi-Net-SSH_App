package com.example.reddragon.remote_connection_master_app.Model.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by blackjack on 4/24/17.
 */

public class StoreCommandsDB extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;

    private static final String DATABASE_NAME = "Stored_Profile.db";
    private static final String TABLE_NAME = "commands_table";

    private static final String COL_1 = "COMMANDS";

    private static final int DATABASE_VERSION = 1;

    public StoreCommandsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " +TABLE_NAME+ " (COMMANDS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData( String commands ){

        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(COL_1, commands);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if(result == -1){ return false; } else{ return true; }
    }

    public Cursor getAllData() {
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null); }



    public boolean updateData(String username, String type,
                              String key, String password, String commands){

        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(COL_1, commands);

        sqLiteDatabase.update(TABLE_NAME, contentValues, "COMMANDS = ?", new String[]{ commands } );

        return true;
    }

    public void deleteData(String username){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "COMMANDS = ?", new String[]{username});
    }

}
