/*
 * Copyright 2009 Cedric Priscal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.hubrox.peripherals;

import android.content.Context;
import android.content.Intent;
import android.device.PrinterManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.hubrox.hubroxpayment.R;
import com.example.hubrox.hubroxpayment.SettingsActivity;

/**
 * Created by Jeff Wang on 2016/4/6.
 */
public class Printer {

    PrinterManager printerManager = new PrinterManager();
    SettingsActivity settingsActivity = new SettingsActivity();
    private Context context = null;

//    private Context applicationContext = Printer.this;

    public void doPrint(int type) {

        printerManager.setupPage(384, -1);
        switch (type) {
            case 1:
//                String text = printInfo.getText().toString();
//                printerManager.drawBarcode(text, 196, 300, 20, 2, 70, 0);
//                printerManager.drawBarcode(text, 196, 300, 20, 2, 70, 1);
//                printerManager.drawBarcode(text, 196, 300, 20, 2, 70, 2);
//                printerManager.drawBarcode(text, 196, 300, 20, 2, 70, 3);
                break;

            case 2:
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
                opts.inDensity = this.context.getResources().getDisplayMetrics().densityDpi;
                opts.inTargetDensity = this.context.getResources().getDisplayMetrics().densityDpi;
                Bitmap img = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.ticket, opts);
                printerManager.drawBitmap(img, 30, 0);
                break;

            case 3:
//                String str1 = PreferenceManager.getDefaultSharedPreferences(this.context).getString("print_header", "");
                String header = "Hubrox\r\n";
                printerManager.drawTextEx(header, 0, 0, 300, -1, "arial", 30, 0, 0, 0);
                String footer = "Born to Innovate\r\n\n\n";
                printerManager.drawTextEx(footer, 0, 45, 300, -1, "arial", 25, 0, 0, 0);
                break;
            case 4:
                printerManager.drawLine(264, 50, 48, 50, 4);
                printerManager.drawLine(156, 0, 156, 120, 2);
                printerManager.drawLine(16, 0, 300, 100, 2);
                printerManager.drawLine(16, 100, 300, 0, 2);
        }

        int ret = printerManager.printPage(0);
        Intent intent = new Intent("urovo.prnt.message");
        intent.putExtra("ret", ret);
//        this.sendBroadcast(intent);
    }

//    public void doPrintWork(String msg) {
//        Intent intentService = new Intent(this, PrintBillService.class);
//        intentService.putExtra("SPRT", msg);
//        startService(intentService);
//    }
}
