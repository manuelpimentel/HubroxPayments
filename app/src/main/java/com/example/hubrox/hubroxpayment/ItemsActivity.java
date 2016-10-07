package com.example.hubrox.hubroxpayment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.device.ScanManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class ItemsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TableLayout tableLayout;
    TableRow row;
    SQLController sqlController;
    private AlertDialog.Builder dialogBuilder;
    private ScanManager scanManager;

    private void addDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final Context context = this;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.add_dialog, null);
        dialogBuilder.setView(v);
        final EditText itemCodeEditText = (EditText) v.findViewById(R.id.itemCodeEditText);
        final EditText descriptionEditText = (EditText) v.findViewById(R.id.descriptionEditText);
        final EditText priceEditText = (EditText) v.findViewById(R.id.priceEditText);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tableLayout.removeAllViews();
                String itemCode = itemCodeEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String price = priceEditText.getText().toString();

                itemCodeEditText.setText("");
                descriptionEditText.setText("");
                priceEditText.setText("");

                // inserting data
                sqlController.open();
                sqlController.insertData(itemCode, description, price);
                BuildTable();

                Toast.makeText(getBaseContext(), "Registration Success", Toast.LENGTH_LONG).show();
            }
        });

        dialogBuilder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Do you really want to delete this item?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                tableLayout.removeAllViews();
                                String itemCode = itemCodeEditText.getText().toString();
                                sqlController.open();
                                sqlController.deleteData(itemCode);
                                BuildTable();
                                Toast.makeText(getBaseContext(), "Item Succesfully Deleted", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialogAdd = dialogBuilder.create();
        dialogAdd.show();
    }

    public void editItem(View view) {
        // editing data
        dialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Edit")
                .setMessage("Please type the barcode of the item");
        final Context context = this;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.delete_dialog, null);
        dialogBuilder.setView(v);

        final EditText itemCodeEditText = (EditText) v.findViewById(R.id.itemCodeEditText);


        dialogBuilder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String itemCode = itemCodeEditText.getText().toString();

                sqlController.open();

                Cursor c = sqlController.getItem(itemCode);

                new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.add_dialog, null);
                dialogBuilder.setView(v);
                final EditText itemCodeEditText = (EditText) v.findViewById(R.id.itemCodeEditText);
                final EditText descriptionEditText = (EditText) v.findViewById(R.id.descriptionEditText);
                final EditText priceEditText = (EditText) v.findViewById(R.id.priceEditText);

                itemCodeEditText.setText(c.getString(1), TextView.BufferType.NORMAL);
                descriptionEditText.setText(c.getString(2), TextView.BufferType.EDITABLE);
                priceEditText.setText(c.getString(3), TextView.BufferType.EDITABLE);


                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tableLayout.removeAllViews();
                        String itemCode = itemCodeEditText.getText().toString();
                        String description = descriptionEditText.getText().toString();
                        String price = priceEditText.getText().toString();

                        itemCodeEditText.setText("");
                        descriptionEditText.setText("");
                        priceEditText.setText("");

                        // inserting data
                        sqlController.open();
                        sqlController.editData(itemCode, description, price);
                        BuildTable();

                        Toast.makeText(getBaseContext(), "Changes has been made", Toast.LENGTH_LONG).show();
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialogAdd = dialogBuilder.create();
                dialogAdd.show();

            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialogAdd = dialogBuilder.create();
        dialogAdd.show();
    }


    public void deleteItem(View view) {
        // deleting data

        dialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Please type the barcode of the item");
        final Context context = this;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.delete_dialog, null);
        dialogBuilder.setView(v);

        final EditText itemCodeEditText = (EditText) v.findViewById(R.id.itemCodeEditText);

        dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Do you really want to delete this item?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                tableLayout.removeAllViews();
                                String itemCode = itemCodeEditText.getText().toString();
                                sqlController.open();
                                sqlController.deleteData(itemCode);
                                BuildTable();
                                Toast.makeText(getBaseContext(), "Item Succesfully Deleted", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialogAdd = dialogBuilder.create();
        dialogAdd.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sqlController = new SQLController(this);

        tableLayout = ((TableLayout) findViewById(R.id.itemsTableLayout));

        BuildTable();

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });

////        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
////        add.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                addDialog();
////            }
////        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void BuildTable() {

        sqlController.open();
        Cursor c = sqlController.readEntry(true);

        int rows = c.getCount();
        int cols = c.getColumnCount();

        c.moveToFirst();

        // outer for loop
        for (int i = 0; i < rows; i++) {

            row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 0; j < cols; j++) {

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
//        getMenuInflater().inflate(R.menu.items, menu);
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
