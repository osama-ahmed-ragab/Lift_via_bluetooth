package com.example.litf_via_blutooth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditeNamesActivity extends AppCompatActivity {

    TextView tvDers;
    EditText etDers, etDersSaati;
    EditText etNot;
    LinearLayout LayoutDers;
    ArrayAdapter<String> adapter;
    ListView list;
    ArrayList<String> mylist;
    int pos =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_names);

        Intent intent = getIntent();
        int floor = Integer.parseInt(intent.getStringExtra("Floors"));
        int garage = Integer.parseInt(intent.getStringExtra("Garages"));
        boolean from_shared = intent.getBooleanExtra("from_shared",false);
        mylist = new ArrayList<>();
        for(int i=floor;i>=-garage;i--){
            if (i<0){
                String temp = "G"+ -i;
                mylist.add(temp);
            }else mylist.add(i+"");
        }
        mylist.add("Alarm");
        mylist.add("Fan");
        mylist.add("Stop");


        if (from_shared){
            Intent intent2 = new Intent(this, GridActivity.class);
            intent2.putExtra("adopter_list", mylist);
            startActivity(intent2);
        }
        list = (ListView) findViewById(R.id.listView1);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mylist);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_big,mylist);

        list.setAdapter(adapter);

        etDers = new EditText(this);
        //Dialog
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Edit Name :");
        build.setView(etDers);

        build.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mylist.set(pos,etDers.getText().toString());
                adapter.notifyDataSetChanged();            }
        });
        build.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        }); //End of alert.setNegativeButton

        final AlertDialog alertDers = build.create();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etDers.setText (((TextView)view).getText() );
                pos = position;
                alertDers.show();
            }
        });

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
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);


    }
    public void finish(View view) {
        Intent intent = new Intent(this, GridActivity.class);
        intent.putExtra("adopter_list", mylist);
        startActivity(intent);
//        this.finish();
    }

}