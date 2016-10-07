package com.example.hubrox.hubroxpayment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SQLController {

    private MyDbHelper myDbHelper;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public SQLController(Context c) {
        context = c;
    }

    public SQLController open() throws SQLException {
        myDbHelper = new MyDbHelper(context);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        myDbHelper.close();
    }

    public void insertData(String code, String desc, String price) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper.USER_CODE, code);
        cv.put(MyDbHelper.USER_DESC, desc);
        cv.put(MyDbHelper.USER_PRICE, price);
        sqLiteDatabase.insert(MyDbHelper.TABLE_NAME, null, cv);
    }


    public void insertPayment(String Amount, String Card) {
        // TODO Auto-generated method stub

        DateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        dateFormatter.setLenient(false);
        Date today = new Date();
        String s = dateFormatter.format(today);


        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper.LP_DATE, s);
        cv.put(MyDbHelper.LP_CREDITCARD, Card);
        cv.put(MyDbHelper.LP_AMOUNT, Amount);
        sqLiteDatabase.insert(MyDbHelper.LP_TABLE_NAME, null, cv);
    }

    public void deleteData(String code) {
        sqLiteDatabase.delete(myDbHelper.TABLE_NAME, myDbHelper.USER_CODE + "=" + code, null);
    }

    public void editData(String code, String desc, String price) {
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper.USER_DESC, desc);
        cv.put(MyDbHelper.USER_PRICE, price);
        sqLiteDatabase.update(myDbHelper.TABLE_NAME, cv, myDbHelper.USER_CODE + "=" + code, null);
    }

    public Cursor getItem(String code) {
        String[] allColumns = new String[]{MyDbHelper.USER_ID, MyDbHelper.USER_CODE, MyDbHelper.USER_DESC, MyDbHelper.USER_PRICE};

        Cursor c = sqLiteDatabase.query(MyDbHelper.TABLE_NAME, null, myDbHelper.USER_CODE + " = " + code, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;


    }

    public Cursor readEntry(Boolean mod) {
        // TODO Auto-generated method stub
        String[] allColumns = null;
        Cursor c;

        if (mod) {
            allColumns = new String[]{MyDbHelper.USER_ID, MyDbHelper.USER_CODE,
                    MyDbHelper.USER_DESC, MyDbHelper.USER_PRICE};
            c = sqLiteDatabase.query(MyDbHelper.TABLE_NAME, allColumns, null, null, null,
                    null, null);
        } else {
            allColumns = new String[]{MyDbHelper.LP_ID, MyDbHelper.LP_DATE,
                    MyDbHelper.LP_AMOUNT, MyDbHelper.LP_CREDITCARD};
            c = sqLiteDatabase.query(MyDbHelper.LP_TABLE_NAME, allColumns, null, null, null,
                    null, null);
        }

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


}
