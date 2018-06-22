package com.example.karaens.taskforce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static String DATABASE_NAME="Backup.db";
    public static String TABLE_NAME="Tasks_Table";
    public static String COL1="ID";
    public static String COL2="HEADING";
    public static String COL3="DSCRPTN";
    public static String COL4="CODE";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,HEADING TEXT,DSCRPTN TEXT,CODE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    boolean addData(String id,String heading,String dscrptn,int code){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2,heading);
        contentValues.put(COL3,dscrptn);
        contentValues.put(COL4,code);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }

    Cursor getData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    boolean updateData(String id,String heading,String dscrptn,int code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, heading);
        contentValues.put(COL3, dscrptn);
        contentValues.put(COL4, code);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[] { id });
        return true;
    }

    int deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int res=db.delete(TABLE_NAME,"ID=?",new String[] { id });
        return res;
    }

    int resetData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
        Cursor rows=getData();
        if(rows.getCount()==0){
            return 0;
        }
        else
            return 1;
    }
}
