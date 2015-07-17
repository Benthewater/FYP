package com.example.fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tan on 7/13/2015.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HangOut.db";
    public static final String TABLE_MEMBER = "Member";
    public static final String TABLE_SHOP = "Shop";
    public static final String TABLE_FRIEND = "Friend";
    public static final String TABLE_PROMOTION = "Promotion";
    public static final String KEY_MEMBER_ID = "Member_ID";
    public static final String KEY_MEMBER_NAME = "Member_Name";
    public static final String KEY_MEMBER_POINT = "Member_Point";
    public static final String CREATE_TABLE_MEMBER = "CREATE TABLE " + TABLE_MEMBER + "(" + KEY_MEMBER_ID + " INTEGER PRIMARY KEY," + KEY_MEMBER_NAME + " TEXT," + KEY_MEMBER_POINT + " int" + ")";;
    public static final String CREATE_TABLE_SHOP = "";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEMBER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST" + TABLE_MEMBER);
        onCreate(db);
    }

    public void StoreMemberInfo(String member_Name, int member_Point){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_ID, member_ID);
        values.put(KEY_MEMBER_NAME, member_Name);
        values.put(KEY_MEMBER_POINT, member_Point);
        db.insert(TABLE_MEMBER, null, values);
    }

    public Cursor getName(int points){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select Member_Name FROM Member WHERE Member_Point =" + points + "", null);
        return res;
    }

}
