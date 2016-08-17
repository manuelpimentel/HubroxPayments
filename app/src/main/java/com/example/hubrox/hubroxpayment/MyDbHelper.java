package com.example.hubrox.hubroxpayment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    // TABLE INFORMATTION
    public static final String TABLE_NAME = "Items";
    public static final String USER_ID = "ID";
    public static final String USER_CODE = "Code";
    public static final String USER_DESC = "Description";
    public static final String USER_PRICE = "Price";

    // DATABASE INFORMATION
    static final String DB_NAME = "Hubrox.db";
    static final int DB_VERSION = 2;

    // TABLE CREATION STATEMENT

    private static final String CREATE_TABLE = "create table " + TABLE_NAME
            + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_CODE + " TEXT NOT NULL ,"
            + USER_DESC + " TEXT NOT NULL ,"
            + USER_PRICE + " TEXT NOT NULL);";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}