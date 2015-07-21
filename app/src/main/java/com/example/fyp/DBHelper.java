package com.example.fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tan on 7/13/2015.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HangOut.db";
    public static final String TABLE_MEMBER = "Member";
    public static final String KEY_MEMBER_ID = "Member_ID";
    public static final String KEY_MEMBER_NAME = "Member_Name";
    public static final String KEY_MEMBER_POINT = "Member_Point";
    public static final String TABLE_SHOP = "Shop";
    public static final String KEY_SHOP_ID = "Shop_ID";
    public static final String KEY_SHOP_NAME = "Shop_Name";
    public static final String TABLE_FRIEND = "Friend";
    public static final String KEY_FRIEND_ID = "Friend_ID";
    public static final String KEY_FRIEND_NAME = "Friend_Name";
    public static final String TABLE_PROMOTION = "Promotion";
    public static final String KEY_PROMOTION_ID = "Promotion_ID";
    public static final String KEY_PROMOTION_DESCRIPTION = "Promotion_Description";

    public static final String CREATE_TABLE_MEMBER = "CREATE TABLE " + TABLE_MEMBER + "(" + KEY_MEMBER_ID + " INTEGER PRIMARY KEY," + KEY_MEMBER_NAME + " TEXT," + KEY_MEMBER_POINT + " int" + ")";
    public static final String CREATE_TABLE_SHOP = "CREATE TABLE " + TABLE_SHOP + "(" + KEY_SHOP_ID + " INTEGER PRIMARY KEY," + KEY_SHOP_NAME + " TEXT" + ")";
    public static final String CREATE_TABLE_FRIEND = "CREATE TABLE " + TABLE_FRIEND + "(" + KEY_FRIEND_ID + " INTEGER PRIMARY KEY," + KEY_FRIEND_NAME + " TEXT" + ")";
    public static final String CREATE_TABLE_PROMOTION = "CREATE TABLE " + TABLE_PROMOTION + "(" + KEY_PROMOTION_ID + " INTEGER PRIMARY KEY," + KEY_PROMOTION_DESCRIPTION + " TEXT" + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cleartable();
        db.execSQL(CREATE_TABLE_MEMBER);
        db.execSQL(CREATE_TABLE_SHOP);
        db.execSQL(CREATE_TABLE_FRIEND);
        db.execSQL(CREATE_TABLE_PROMOTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST" + TABLE_MEMBER);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_SHOP);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_FRIEND);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_PROMOTION);
        onCreate(db);
    }

    public void StoreMemberInfo(int member_ID, String member_Name, int member_Point){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MEMBER_ID, member_ID);
        values.put(KEY_MEMBER_NAME, member_Name);
        values.put(KEY_MEMBER_POINT, member_Point);
        db.insert(TABLE_MEMBER, null, values);
    }

    public void StoreShopInfo(int shop_ID, String shop_Name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SHOP_ID, shop_ID);
        values.put(KEY_SHOP_NAME, shop_Name);
        db.insert(TABLE_SHOP, null, values);
    }

    public void StoreFriendInfo(int friend_ID, String friend_Name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FRIEND_ID, friend_ID);
        values.put(KEY_FRIEND_NAME, friend_Name);
        db.insert(TABLE_FRIEND, null, values);
    }

    public void StorePromotionInfo(int promotion_ID, String promotion_Name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PROMOTION_ID, promotion_ID);
        values.put(KEY_PROMOTION_DESCRIPTION, promotion_Name);
        db.insert(TABLE_PROMOTION, null, values);
    }

    public Cursor getName(int points){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select Member_Name FROM Member WHERE Member_Point =" + points + "", null);
        //Cursor res = db.rawQuery("Select Shop_Name FROM Shop WHERE Shop_ID = 4", null);
        return res;
    }

    public void cleartable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MEMBER);
        db.execSQL("DELETE FROM " + TABLE_SHOP);
        db.execSQL("DELETE FROM " + TABLE_FRIEND);
        db.execSQL("DELETE FROM " + TABLE_PROMOTION);
    }

    public List<String> getShop(){
        List<String> Shop_Name = new ArrayList<String>();

        String query = "SELECT " +  KEY_SHOP_NAME + " FROM " + TABLE_SHOP;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Shop_Name.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return Shop_Name;
    }

    public List<String> getFriend(){
        List<String> Friend = new ArrayList<String>();

        String query = "SELECT " + KEY_FRIEND_NAME + " FROM " + TABLE_FRIEND;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Friend.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return Friend;
    }
}
