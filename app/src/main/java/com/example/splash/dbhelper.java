package com.example.splash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbhelper extends SQLiteOpenHelper {
    public dbhelper(Context context) {
        super(context, "userdata.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table userdetails(username TEXT primary key, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists userdetails");

    }

    public Boolean insertdata(String username,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result=db.insert("userdetails",null,contentValues);
        if(result==-1){
            return  false;
        }
        else {
            return true;
        }
    }

    public Boolean updatedata(String username,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        Cursor cursor = db.rawQuery("Select * from userdetails where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {


        long result = db.update("userdetails",  contentValues,"username=?",new String[] {username});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
      }else {
            return  false;
        }

    }

    public Boolean deletedata(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from userdetails where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {

            long result = db.delete("userdetails",  "username=?",new String[] {username});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return  false;
        }

    }

    public Cursor getdata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from userdetails ", null);
        return cursor;

    }




}




