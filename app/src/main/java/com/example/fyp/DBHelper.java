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
    public static final String KEY_PROMOTION_POINT = "Promotion_Point";
    public static final String TABLE_FRIEND_REQUEST = "Friend_Request";
    public static final String KEY_FRIEND_REQUEST_ID = "Friend_Request_ID";
    public static final String KEY_FRIEND_REQUEST_NAME = "Friend_Request_Name";

    public static final String CREATE_TABLE_MEMBER = "CREATE TABLE " + TABLE_MEMBER + "(" + KEY_MEMBER_ID + " INTEGER PRIMARY KEY," + KEY_MEMBER_NAME + " VARCHAR(25)," + KEY_MEMBER_POINT + " int" + ")";
    public static final String CREATE_TABLE_SHOP = "CREATE TABLE " + TABLE_SHOP + "(" + KEY_SHOP_ID + " INTEGER PRIMARY KEY," + KEY_SHOP_NAME + " VARCHAR(25)" + ")";
    public static final String CREATE_TABLE_FRIEND = "CREATE TABLE " + TABLE_FRIEND + "(" + KEY_FRIEND_ID + " INTEGER PRIMARY KEY," + KEY_FRIEND_NAME + " VARCHAR(25)" + ")";
    public static final String CREATE_TABLE_PROMOTION = "CREATE TABLE " + TABLE_PROMOTION + "(" + KEY_PROMOTION_ID + " INTEGER PRIMARY KEY," + KEY_PROMOTION_DESCRIPTION + " TEXT, " + KEY_PROMOTION_POINT + " INTEGER" + ")";
    public static final String CREATE_TABLE_FRIEND_REQUEST = "CREATE TABLE " + TABLE_FRIEND_REQUEST + "(" + KEY_FRIEND_REQUEST_ID + " INTEGER PRIMARY KEY," + KEY_FRIEND_REQUEST_NAME + " VARCHAR(25)" + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEMBER);
        db.execSQL(CREATE_TABLE_SHOP);
        db.execSQL(CREATE_TABLE_FRIEND);
        db.execSQL(CREATE_TABLE_PROMOTION);
        db.execSQL(CREATE_TABLE_FRIEND_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST" + TABLE_MEMBER);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_SHOP);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_FRIEND);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_PROMOTION);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_FRIEND_REQUEST);
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

    public void StorePromotionInfo(int promotion_ID, String promotion_Name, int promotion_Point){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PROMOTION_ID, promotion_ID);
        values.put(KEY_PROMOTION_DESCRIPTION, promotion_Name);
        values.put(KEY_PROMOTION_POINT, promotion_Point);
        db.insert(TABLE_PROMOTION, null, values);
    }

    public void StoreFriendRequestInfo(int friend_request_ID, String friend_request_Name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FRIEND_REQUEST_ID, friend_request_ID);
        values.put(KEY_FRIEND_REQUEST_NAME, friend_request_Name);
        db.insert(TABLE_FRIEND_REQUEST, null, values);
    }

    public Cursor getName(int points){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select Member_Name FROM Member WHERE Member_Point =" + points + "", null);
        return res;
    }

    public int getmemberID(){
        int id=0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select Member_ID FROM Member", null);

        if(res.moveToFirst()){
            do{
                id = res.getInt(0);
            } while(res.moveToNext());
        }

        res.close();
        db.close();
        return id;
    }

    public int getmemberpoint(){
        int id=0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select Member_Point FROM Member", null);

        if(res.moveToFirst()){
            do{
                id = res.getInt(0);
            } while(res.moveToNext());
        }

        res.close();
        db.close();
        return id;
    }

    public int getshopID(String shopname){
        int id =0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select Shop_ID FROM Shop WHERE Shop_Name = '" + shopname + "'", null);

        if(res.moveToFirst()){
            do{
                id = res.getInt(0);
            } while(res.moveToNext());
        }

        res.close();
        db.close();
        return id;
    }

    public int getFriendRequestID(String requestname){
        int id=0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select Friend_Request_ID FROM Friend_Request WHERE Friend_Request_Name = '" + requestname + "'", null);

        if(res.moveToFirst()){
            do{
                id = res.getInt(0);
            } while(res.moveToNext());
        }

        res.close();
        db.close();
        return id;
    }

    public int getPoints(String description){
        int id=0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select Promotion_Point FROM Promotion WHERE Promotion_Description = '" + description + "'", null);

        if(res.moveToFirst()){
            do{
                id = res.getInt(0);
            } while(res.moveToNext());
        }

        res.close();
        db.close();
        return id;
    }

    public void cleartable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MEMBER);
        db.execSQL("DELETE FROM " + TABLE_SHOP);
        db.execSQL("DELETE FROM " + TABLE_FRIEND);
        db.execSQL("DELETE FROM " + TABLE_PROMOTION);
        db.execSQL("DELETE FROM " + TABLE_FRIEND_REQUEST);
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

    public List<String> getFriendRequest(){
        List<String> FriendRequest = new ArrayList<String>();

        String query = "SELECT " + KEY_FRIEND_REQUEST_NAME + " FROM " + TABLE_FRIEND_REQUEST;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                FriendRequest.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return FriendRequest;
    }

    public List<String> getPromotion(){
        List<String> Promotion = new ArrayList<String>();

        String query = "SELECT " + KEY_PROMOTION_DESCRIPTION + " FROM " + TABLE_PROMOTION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Promotion.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return Promotion;
    }


}
