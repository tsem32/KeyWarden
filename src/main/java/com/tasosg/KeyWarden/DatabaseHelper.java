package com.tasosg.KeyWarden;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final  String DATABASE_NAME="AppDB2.db";
    public static final  String TABLE_NAME="data";
    public static final String  COL_1="TITLE";
    public static final String  COL_2="USERNAME";
    public static final String  COL_3="PASSWORD";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null ,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_1 + " TEXT,"
                + COL_2 + " TEXT,"
                + COL_3 + " TEXT,"+ "COLOR INTEGER)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String title,String username,String password,int color){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(COL_1,title);
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,password);
        contentValues.put("COLOR",color);
        long result= db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if(result==-1)return false;
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="Select * from "+TABLE_NAME;
        Cursor data=db.rawQuery(query,null);
        return data;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where ID="+id+"", null );

        return res;
    }


    public void deleteItem(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME+" where ID='"+id+"'");
    }


    public void empty(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.execSQL("VACUUM");
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");

    }

    public void updateData(int id,String title,String username,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(COL_1,title);
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,password);
        long result= db.update(TABLE_NAME,contentValues,"ID="+id,null);
        db.close();
    }

}
