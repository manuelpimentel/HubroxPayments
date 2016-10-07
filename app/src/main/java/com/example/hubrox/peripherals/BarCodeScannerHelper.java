package com.example.hubrox.peripherals;

/**
 * Created by manuel on 22/08/16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.device.scanner.configuration.Triggering;
import android.util.Log;

import com.example.hubrox.hubroxpayment.CallbackInterface;

public class BarCodeScannerHelper
        extends PeripManager {
    private static final String SCAN_ACTION = "urovo.rcv.message";
    private Context applicationContext;
    private String barcodeStr;
    private CallbackInterface inst;
    private boolean isScaning = false;
    private ScanManager mScanManager;
    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent) {
            BarCodeScannerHelper.this.isScaning = false;
            //paramAnonymousContext = paramAnonymousIntent.getByteArrayExtra("barocode");
            int i = paramAnonymousIntent.getIntExtra("length", 0);
            int j = paramAnonymousIntent.getByteExtra("barcodeType", (byte) 0);
            Log.i("debug", "----codetype--" + j);
            // BarCodeScannerHelper.this.barcodeStr = new String(paramAnonymousContext, 0, i);
            BarCodeScannerHelper.this.inst.callback(BarCodeScannerHelper.this.barcodeStr, "barcode");
        }
    };
    private int soundid;

    public void close() {
        this.mScanManager.stopDecode();
    }

    public void initScan() {
        this.mScanManager = new ScanManager();
        this.mScanManager.openScanner();
        this.mScanManager.switchOutputMode(1);
    }

    public void initScan(CallbackInterface paramCallbackInterface) {
        this.mScanManager = new ScanManager();
        this.mScanManager.openScanner();
        this.inst = paramCallbackInterface;
        this.mScanManager.switchOutputMode(0);
    }

    public void manage() {
        if (this.mScanManager.getTriggerMode() != Triggering.CONTINUOUS) {
            this.mScanManager.setTriggerMode(Triggering.CONTINUOUS);
        }
    }

    public void onPause() {
        super.onPause();
        if (this.mScanManager != null) {
            this.mScanManager.stopDecode();
            this.isScaning = false;
        }
        this.applicationContext.unregisterReceiver(this.mScanReceiver);
    }

    public void onResume() {
        initScan();
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("urovo.rcv.message");
        this.applicationContext.registerReceiver(this.mScanReceiver, localIntentFilter);
    }

    public void scan() {
        this.mScanManager.stopDecode();
        this.isScaning = true;
        try {
            Thread.sleep(100L);
            this.mScanManager.startDecode();
            return;
        } catch (InterruptedException localInterruptedException) {
            for (; ; ) {
                localInterruptedException.printStackTrace();
            }
        }
    }

   /* public void setApplicationContext(Context paramContext)
    {
        this.applicationContext = paramContext;
        paramContext = new IntentFilter();
        paramContext.addAction("urovo.rcv.message");
        this.applicationContext.registerReceiver(this.mScanReceiver, paramContext);
    }*/
}


/* Location:              /Users/Amir/Desktop/dex2jar-2.0/lib/classes.jar!/com/hb9000/peripherals/BarCodeScannerHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */