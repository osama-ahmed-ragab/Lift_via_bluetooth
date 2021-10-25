package com.example.litf_via_blutooth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by OSAMA on 21-05-2021.
 */
public class GridActivity extends AppCompatActivity {

    EditText etDers;
    AlertDialog alertDers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        ArrayList<String> mylist = intent.getStringArrayListExtra("adopter_list");


        GridView grid = (GridView) findViewById(R.id.list);
        GalleryAdapter galleryAdapter = new GalleryAdapter(mylist, this);
        grid.setAdapter(galleryAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView vt = (TextView) view.findViewById(R.id.magnitude);

                Toast.makeText(getApplicationContext(), vt.getText(), Toast.LENGTH_SHORT).show();
                MainActivity.mBluetooth.write((String) vt.getText());

            }
        });


        etDers = new EditText(this);
        etDers.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //Dialog
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Enter password :");
        build.setView(etDers);

        build.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(etDers.getText().toString().equals("EGEC0000")){
                    GridActivity.this.finish();
                    etDers.getText().clear();

                }
                else {
                    showToast();

                }
            }
        });
        build.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        }); //End of alert.setNegativeButton

        alertDers = build.create();


        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.mybutton);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                alertDers.show();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(getApplicationContext(), "Can't go back :) ", Toast.LENGTH_SHORT).show();
        alertDers.show();
//        finish();

    }

    public void showToast() {
        Toast.makeText(this,"Wrong Password", Toast.LENGTH_SHORT).show();
    }
}