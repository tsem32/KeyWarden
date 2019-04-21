package com.tasosg.KeyWarden;

import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements addActivity.ExampleDialogListener ,detailsActivity.ExampleDialogListener,editActivity.ExampleDialogListener  {
    private LinearLayout parentLinearLayout;
    private FloatingActionButton button;
    private DatabaseHelper dbhelper;
    public int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentLinearLayout = (LinearLayout) findViewById(R.id.myroot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbhelper=new DatabaseHelper(this);

        Cursor data=dbhelper.getData();
        while(data.moveToNext()){
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.field, null);
            //Create list of items
            TextView textView= (TextView) rowView.findViewWithTag("empty"); //Main text
         //   textView.setTextColor(Color.parseColor("#ff303f9f"));
           textView.setTextColor(data.getInt(4));
            textView.setTextSize(24);
            textView.setTag(data.getInt(0));                   //Tag used to store id
            textView.setText(data.getString(1));                //Main text
            textView= (TextView) rowView.findViewWithTag("empty_more"); //Set item id in more tag
            textView.setTag(data.getInt(0));
            textView= (TextView) rowView.findViewWithTag("empty_del");  //Set item id in less tag
            textView.setTag(data.getInt(0));
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_view:
                openDialog();
                return true;

            case R.id.action_deleteall_view:
                new AlertDialog.Builder(this)
                        .setTitle("Delete All")
                        .setMessage("Do you really want to erase all data?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                dbhelper.empty();

                                Toast.makeText(MainActivity.this, "Deleted all entries", Toast.LENGTH_SHORT).show();
                                Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(refresh);
                                MainActivity.this.finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

                return true;

            case R.id.action_exit_view:
                finish();
                return true;
        }

        return false;
    }

    //------------------------------------SWIPE LAYOUT EVENT-------------------------------
    public void openDialog() {
        addActivity exampleDialog = new addActivity();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    public void openDetails(int id){

        detailsActivity details=new detailsActivity();
        Bundle args = new Bundle();
        args.putInt("id",id);
        details.setArguments(args);
        details.show(getSupportFragmentManager(), "example dialog");
    }
    public void layoutOneOnClick(View v) {
        TextView tx= (TextView) v.findViewById(R.id.temp_id);
        openDetails(Integer.parseInt(tx.getTag().toString()));

    }

    public void moreOnClick(View v) {
        editActivity edit = new editActivity();
        int id=Integer.parseInt(v.getTag().toString());
        Bundle args = new Bundle();
        args.putInt("id",id);
        edit.setArguments(args);
        edit.show(getSupportFragmentManager(), "example dialog");
         //   openDetails(Integer.parseInt(v.getTag().toString()));
        //Toast.makeText(MainActivity.this, v.getTag().toString(), Toast.LENGTH_SHORT).show();
    }

    public void deleteOnClick(final View v) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Do you really want delete this item?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dbhelper.deleteItem(Integer.parseInt(v.getTag().toString()));

                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(refresh);
                        MainActivity.this.finish();
                    }})
                .setNegativeButton(android.R.string.no, null).show();





    }
    //----------------------------------------------------------------------------------------------

    //--------------------------NOT USED------------------------
    @Override
    public void applyTexts(String username, String password) {

    }
    //-------------------------------------------------------



}
