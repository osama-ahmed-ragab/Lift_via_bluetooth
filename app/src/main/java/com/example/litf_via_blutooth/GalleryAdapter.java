package com.example.litf_via_blutooth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter implements View.OnClickListener{
    private ArrayList<String> users;
    private Context context;

    public GalleryAdapter(ArrayList<String> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.lift_list_item,null);
        TextView floor;
        if(view==null){
            floor=new TextView(context);
        }
        floor=(TextView)view.findViewById(R.id.magnitude);
        String user=users.get(i);
        floor.setText(user);
        return view;
    }

    @Override
    public void onClick(View view) {
//
//        TextView vt = (TextView)view;
//
//        Toast.makeText(getApplicationContext(),vt.getText(),Toast.LENGTH_SHORT).show();
//        MainActivity.mBluetooth.write((String) vt.getText());
    }



}
