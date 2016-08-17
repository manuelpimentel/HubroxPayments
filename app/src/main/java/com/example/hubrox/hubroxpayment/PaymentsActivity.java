package com.example.hubrox.hubroxpayment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class PaymentsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TableLayout tableLayout;
    TextView totalPrice;

    private ScanManager scanManager;
    private boolean isScanning = false;
    private SoundPool soundPool = null;
    private int soundId;
    private Vibrator vibrator;
    private String barcodeStr;
    private EditText showScanResult;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            isScanning = false;
            soundPool.play(soundId, 1, 1, 0, 0, 1);
            showScanResult.setText("");
            vibrator.vibrate(100);

            byte[] barcode = intent.getByteArrayExtra("barcode");
            //byte[] barcode = intent.getByteArrayExtra("barcode");
            int barcodeLen = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            android.util.Log.i("debug", "----codetype--" + temp);
            barcodeStr = new String(barcode, 0, barcodeLen);
//            Context context1 = getApplicationContext();
//            Toast toast = Toast.makeText(context1, barcodeStr, Toast.LENGTH_LONG);
//            toast.show();
            showScanResult.setText(barcodeStr);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.tableLayout = ((TableLayout) findViewById(R.id.paymentsTableLayout));
        this.totalPrice = ((TextView) findViewById(R.id.totalPrice));

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        showScanResult = (EditText) findViewById(R.id.scanResult);

//        startScanner();

        Button scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanManager.stopDecode();
                isScanning = true;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                scanManager.startDecode();
            }
        });

        Button swipeButton = (Button) findViewById(R.id.swipeButton);
        swipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentsActivity.this, MagManagerActivity.class);
                startActivity(intent);
            }
        });

        Button insertButton = (Button) findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentsActivity.this, ICCActivity.class);
                startActivity(intent);
            }
        });

        Button tapButton = (Button) findViewById(R.id.tapButton);
        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentsActivity.this, NFCActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void initScan() {
        // TODO Auto-generated method stub
        scanManager = new ScanManager();
        scanManager.openScanner();

        scanManager.switchOutputMode(0);
        soundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
        soundId = soundPool.load("/etc/Scan_new.ogg", 1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.payments, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_items) {
            startActivity(new Intent(this, ItemsActivity.class));
        } else if (id == R.id.nav_payments) {
            startActivity(new Intent(this, PaymentsActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_main) {
            startActivity(new Intent(this, MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        if (scanManager != null) {
            scanManager.stopDecode();
            isScanning = false;
        }
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initScan();
//        showScanResult.setText("");
        IntentFilter filter = new IntentFilter();
//        filter.addAction(SCAN_ACTION);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }
}