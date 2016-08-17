package com.example.hubrox.hubroxpayment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int RESULT_LOAD_IMAGE = 1;
    public String headText;
    public String footText;
    String logoPath;
    Bitmap bmpLogo = null;

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if ((paramInt1 == RESULT_LOAD_IMAGE) && (paramInt2 == -1) && (paramIntent != null)) {
            Uri localUri = paramIntent.getData();
            String[] arrayOfString = {"_data"};
            Cursor localCursor = getContentResolver().query(localUri, arrayOfString, null, null, null);
            localCursor.moveToFirst();
            String str = localCursor.getString(localCursor.getColumnIndex(arrayOfString[0]));
            localCursor.close();
            ImageView localImageView = (ImageView) findViewById(R.id.imgView);
            this.bmpLogo = BitmapFactory.decodeFile(str);
            this.logoPath = str;
            localImageView.setImageBitmap(this.bmpLogo);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button selectLogo = (Button) findViewById(R.id.selectLogo);
        selectLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                SettingsActivity.this.startActivityForResult(localIntent, SettingsActivity.RESULT_LOAD_IMAGE);
            }
        });
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
//        getMenuInflater().inflate(R.menu.settings, menu);
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

    protected void onPause() {
        super.onPause();
        this.headText = ((EditText) findViewById(R.id.headText)).getText().toString();
        this.footText = ((EditText) findViewById(R.id.footText)).getText().toString();
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        localEditor.putString("print_header", this.headText);
        localEditor.putString("print_footer", this.footText);
        localEditor.putString("print_logo", this.logoPath);
        localEditor.commit();
    }

    protected void onResume() {
        this.headText = PreferenceManager.getDefaultSharedPreferences(this).getString("print_header", "");
        this.footText = PreferenceManager.getDefaultSharedPreferences(this).getString("print_footer", "");
        this.logoPath = PreferenceManager.getDefaultSharedPreferences(this).getString("print_logo", "");
        ((EditText) findViewById(R.id.headText)).setText(this.headText);
        ((EditText) findViewById(R.id.footText)).setText(this.footText);
        super.onResume();
    }
}
