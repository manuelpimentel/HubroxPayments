package com.example.hubrox.hubroxpayment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NFCActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
    }

    @SuppressLint("NewApi")
    protected void checkNfc() {
        if (!nfcAdapter.isEnabled()) {
            startActivity(new Intent(
                    android.provider.Settings.ACTION_NFC_SETTINGS));
        }
        // try {
        // nfcAdapter.wait();
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }
}
