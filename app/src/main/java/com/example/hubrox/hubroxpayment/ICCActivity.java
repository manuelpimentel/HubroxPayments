package com.example.hubrox.hubroxpayment;

import android.database.Cursor;
import android.device.IccManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hubrox.peripherals.ICCardConvert;
import com.example.hubrox.peripherals.Printer;

import java.util.ArrayList;

public class ICCActivity extends AppCompatActivity {

    byte[] apdu_utf = {
            0x00, (byte) 0xA4, 0x04, 0x00, 0x0E, 0x31, 0x50, 0x41, 0x59, 0x2E, 0x53,
            0x59, 0x53, 0x2E, 0x44, 0x44, 0x46, 0x30, 0x31, 0x00
    };
    Printer printerManager = new Printer();
    private IccManager mIccReader;
    private EditText editText;

    SQLController sqlController;

    float total = 0;
    ArrayList<String> itemCodes = null;
    Payment payment;
    ArrayList<Item> itemList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icc);

        sqlController = new SQLController(this);
        sqlController.open();

        itemList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        total = bundle.getFloat("TOTAL");
        itemCodes = bundle.getStringArrayList("ITEM_CODES");

        payment = new Payment(itemCodes,total);


        editText = (EditText) findViewById(R.id.editText);

        Button defApduButton = (Button) findViewById(R.id.DEFAPDUButton);
        defApduButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCmd(apdu_utf, 3);
            }
        });

        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] atr = new byte[64];
                int retLen = mIccReader.activate(atr);
                // 6.print the atr
                if (retLen == -1) {
                    editText.append("IC Card reset failed......." + "\n");
                } else {
                    editText.append("ATR: " + ICCardConvert.bytesToHexString(atr, 0, retLen) + "\n");
                }
            }
        });

        Button detectButton = (Button) findViewById(R.id.detectButton);
        detectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIccReader.open((byte) 0, (byte) 0x01, (byte) 0x01);
                int status = mIccReader.detect();
                if (status != 0) {
                    editText.append("Please insert IC Card......." + status + "\n");
                } else {
                    for (int j = 0; j < itemCodes.size(); j++){
                        String itemCode = itemCodes.get(j);
                        Cursor c = sqlController.getItem(itemCode);
                        String desc = c.getString(2);
                        String code = c.getString(1);
                        float total = Float.parseFloat(c.getString(3));
                        Item item = new Item(desc,code,total);
                        itemList.add(item);
                    }

                    printerManager.doPrint(3,payment,itemList);
                }
            }
        });
    }

    private void sendCmd(byte[] cmd, int type) {

        // 4.select solt one
        /*int status = mIccReader.SelectSlot((byte)1);
        if (status != 0) {
            return;
        }*/
        
        /*status = mIccReader.Detect();
        if (status != 0) {
            editText.append("Please inster IC Card......." + "\n");
            return;
        }*/
        
        /*byte[] atr = new byte[64];
        int retLen = mIccReader.Reset(atr);
        // 6.print the atr
        if (retLen == -1) {
            editText.append(" IC Card reset faile......." + "\n");
            return;
        }
        if(type == 1) {
            editText.append("ATR: " + Convert.bytesToHexString(atr, 0, retLen) + "\n");
            return;
        } */

        int apdu_count = (type == 2) ? cmd.length : apdu_utf.length;
        byte[] rspBuf = new byte[256];
        byte[] rspStatus = new byte[2];
        int retLen = mIccReader.apduTransmit((type == 2) ? cmd : apdu_utf, (char) apdu_count, rspBuf, rspStatus);
        if (retLen == -1) {
            return;
        }
        editText.append("APDU RSP REVC: " + ICCardConvert.bytesToHexString(rspBuf, 0, retLen) + "\n");
        editText.append("APDU RSP REVC Status : " + ICCardConvert.bytesToHexString(rspStatus, 0, 2) + "\n");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mIccReader != null) {
            int ret = mIccReader.deactivate();
            android.util.Log.i("mIccReader", "-----------Eject-----retr=" + ret);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mIccReader = new IccManager();
    }
}
