package com.example.hubrox.hubroxpayment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TableLayout tableLayout;
    SQLController sqlController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sqlController = new SQLController(this);
        this.tableLayout = ((TableLayout) findViewById(R.id.mainTableLayout));
        tableLayout.removeAllViews();
        BuildTable();

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
    }


    private void BuildTable() {
        try {
            sqlController.open();
            Cursor c = sqlController.readEntry(false);
            TableRow row;

            int rows = c.getCount();
            int cols = c.getColumnCount();

            c.moveToFirst();

            // outer for loop
            for (int i = 0; i < rows; i++) {

                row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                // inner for loop
                for (int j = 1; j < cols; j++) {

                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
//                tv.setBackgroundResource(R.drawable.cell_shape);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(15);
                    tv.setPadding(0, 5, 0, 5);

                    tv.setText(c.getString(j));

                    row.addView(tv);

                }

                c.moveToNext();

                tableLayout.addView(row);

            }
            sqlController.close();

                /*

                int i = cursor.getCount();
                int j = cursor.getColumnCount();
                cursor.moveToFirst();
                TableRow tableRow = new TableRow(this);

                int m;
                for (int k = 0; ; k++) {
                    if (k >= j) {
                        this.tableLayout.addView(tableRow);
                        m = 0;
                        if (m < i) {
                            break;
                        }
                        this.sqlController.close();
                        return;
                    }
                    tableRow.setLayoutParams(new TableRow.LayoutParams(-1, -2));
                    TextView localTextView1 = new TextView(this);
                    localTextView1.setLayoutParams(new TableRow.LayoutParams(-2, -2));
                    localTextView1.setGravity(17);
                    localTextView1.setTextSize(18.0F);
                    tableRow.addView(localTextView1);
                }
                TableRow localTableRow2 = new TableRow(this);
                localTableRow2.setLayoutParams(new TableRow.LayoutParams(-1, -2));
                for (int n = 0; ; n++) {
                    if (n >= j) {
                        cursor.moveToNext();
                        this.tableLayout.addView(localTableRow2);
                        m++;
                        break;
                    }
                    TextView localTextView2 = new TextView(this);
                    localTextView2.setLayoutParams(new TableRow.LayoutParams(-2, -2));
                    localTextView2.setGravity(17);
                    localTextView2.setTextSize(15);
                    localTextView2.setPadding(0, 5, 0, 5);
                    localTextView2.setText(cursor.getString(n));
                    localTableRow2.addView(localTextView2);
                }
                */
        } catch (NullPointerException nullPointer) {
            Toast.makeText(getBaseContext(), "No latest payments found", Toast.LENGTH_LONG).show();
        }
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
//        getMenuInflater().inflate(R.menu.main, menu);
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
}
