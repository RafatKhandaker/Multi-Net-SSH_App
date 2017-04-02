package com.example.reddragon.remote_connection_master_app.Model.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RedDragon on 2/16/17.
 */

public class StoreCommandsDB extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    private static final String DATABASE_NAME = "Stored_Commands.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "command_table";
    private ContentValues contentValues;

    private static final String COL_1 = "ID";
    private static final String COL_2 = "COMMAND";


    public StoreCommandsDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_NAME+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                ",COMMAND TEXT,NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData( String Command ){

        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(COL_2, Command);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if(result == -1){ return false; } else{ return true; }
    }

    public Cursor getAllData() {
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
    }

    public boolean updateData(String id, String Command, String Name){

        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, Command);

        sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{ id } );

        return true;
    }

    public Integer deleteData(String command){
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "COMMAND = ?", new String[]{command});
    }

}
