package com.example.hubrox.hubroxpayment;

import android.device.PrinterManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hubrox.peripherals.MagReadService;
import com.example.hubrox.peripherals.Printer;

public class MagManagerActivity extends AppCompatActivity {

    public final static String string = "Hubrox\r\n";
    Printer printer = new Printer();
    SettingsActivity settingsActivity = new SettingsActivity();
    String headText = settingsActivity.headText;
    String footText = settingsActivity.footText;
    PrinterManager printerManager = new PrinterManager();
    private EditText mNo;
    private MagReadService mReadService;
    private ToneGenerator tg = null;
    private TextView mAlertTv;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MagReadService.MESSAGE_READ_MAG:
                    //MyToast.showCrouton(MainActivity.this, "Read the card successed!", Style.CONFIRM);
                    updateAlert("Read the card successed!", 1);
                    beep();
                    String track1 = msg.getData().getString(MagReadService.CARD_TRACK1);
                    //mNo.setText("");
                    mNo.append(" track1: " + track1);
                    mNo.append("\n\n");
                    /*
                    printerManager.setupPage(384, -1);
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    opts.inDensity = getResources().getDisplayMetrics().densityDpi;
                    opts.inTargetDensity = getResources().getDisplayMetrics().densityDpi;
                    Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.hubrox_logo, opts);
                    printerManager.drawBitmap(img, 30, 0);
                    String string = "Hubrox";
                    printerManager.drawTextEx(string, 0, 5, 300, -1, "arial", 25, 0, 0, 0);
                    */
                    printer.doPrint(3);
                    break;
                case MagReadService.MESSAGE_OPEN_MAG:
                    //MyToast.showCrouton(MainActivity.this, "Init Mag Reader faile!", Style.ALERT);
                    updateAlert("Init Mag Reader failed!", 2);
                    break;
                case MagReadService.MESSAGE_CHECK_FAILE:
                    //MyToast.showCrouton(MainActivity.this, "Please Pay by card!", Style.ALERT);
                    updateAlert("Please Pay by card!", 2);
                    break;
                case MagReadService.MESSAGE_CHECK_OK:
                    //MyToast.showCrouton(MainActivity.this, "Pay by card OK!", Style.CONFIRM);
                    updateAlert("Pay by card successed!", 1);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mag_manager);

        mNo = (EditText) findViewById(R.id.magEditText);
        mAlertTv = (TextView) findViewById(R.id.magTextView);
        tg = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
        mReadService = new MagReadService(this, mHandler);
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
        mReadService.stop();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mReadService.start();
    }

    private void updateAlert(String mesg, int type) {
        if (type == 2)
            mAlertTv.setBackgroundColor(Color.RED);
        else
            mAlertTv.setBackgroundColor(Color.GREEN);
        mAlertTv.setText(mesg);

    }

    private void beep() {
        if (tg != null)
            tg.startTone(ToneGenerator.TONE_CDMA_NETWORK_CALLWAITING);
    }
}
