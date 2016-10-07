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

    // LATEST PAYMENTS TABLE INFORMATION
    public static final String LP_TABLE_NAME = "Latest_Payments";
    public static final String LP_ID = "ID";
    public static final String LP_CREDITCARD = "Credit_Card";
    public static final String LP_AMOUNT = "Amount";
    public static final String LP_DATE = "Date";


    // DATABASE INFORMATION
    static final String DB_NAME = "Hubrox.db";
    static final int DB_VERSION = 4;

    // TABLE CREATION STATEMENT

    private static final String CREATE_TABLE = "create table " + TABLE_NAME
            + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_CODE + " TEXT NOT NULL,"
            + USER_DESC + " TEXT NOT NULL,"
            + USER_PRICE + " TEXT NOT NULL)";

    private static final String CREATE_LP_TABLE = "create table " + LP_TABLE_NAME
            + "(" + LP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LP_DATE + " DATETIME NOT NULL,"
            + LP_CREDITCARD + " TEXT NOT NULL,"
            + LP_AMOUNT + " TEXT NOT NULL)";


    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LP_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LP_TABLE_NAME);
        onCreate(db);
    }
}