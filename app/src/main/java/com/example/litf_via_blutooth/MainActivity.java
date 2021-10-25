package com.example.litf_via_blutooth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by OSAMA on 21-05-2021.
 */
public class MainActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    EditText floor;
    EditText garage;
    Button Create, Connect;
    public BluetoothAdapter mBluetoothAdapter;
    protected AlertDialog.Builder builder;
    protected ListView device_list;

    static ConnectThread mBluetooth = new ConnectThread();
    String mBluetoothName = "";
    String mBluetoothAddress = "";
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floor = (EditText) findViewById(R.id.floor);
        garage = (EditText) findViewById(R.id.Garage);
        Create = (Button) findViewById(R.id.create);
        Connect = (Button) findViewById(R.id.connect);
        //Create.setEnabled(false);
        builder = new AlertDialog.Builder(this);
        mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();

        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

//            finish();
            return;
        }

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int floor_pref  = prefs.getInt("floor_count", -1); //0 is the default value.
        if (floor_pref!=-1){
            int garage_perf = prefs.getInt("garage_count", -1); //0 is the default value.
            String bluetooth_address_pref = prefs.getString("bluetooth_address", ""); //0 is the default value.
//            mBluetooth.connect(bluetooth_address_pref);
            Intent intent = new Intent(this, EditeNamesActivity.class);
            String floors = floor.getText().toString();
            String garages = garage.getText().toString();
            intent.putExtra("Floors", floors);
            intent.putExtra("Garages", garages);
            intent.putExtra("from_shared",true);
            startActivity(intent);
        }
        editor = prefs.edit();

    }



    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, EditeNamesActivity.class);
        String floors = floor.getText().toString();
        String garages = garage.getText().toString();
        intent.putExtra("Floors", floors);
        intent.putExtra("Garages", garages);
        intent.putExtra("from_shared",false);
//        editor.putString("bluetooth_address",mBluetoothAddress);
        editor.putInt("floor_count",Integer.parseInt(floors));
        editor.putInt("garage_count",Integer.parseInt(garages));
        editor.apply();
        startActivity(intent);
//        this.finish();
    }


    public void connect (View view ){
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
        } else {
            if (!mBluetooth.mBluetoothAddress.equals("")) {//if another connection is already exits then close it first
                stopAllActivities();
            } else {
                try {
                    Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                    startActivityForResult(serverIntent, Helper.REQUEST_CONNECT_DEVICE);
                } catch (Exception e) {
                    showToast(getString(R.string.errorOccured) + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }


    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void stopAllActivities() {
        mBluetooth.write("S"); //send Stop Signal before it closes the connection
        mBluetooth.mBluetoothAddress = ""; // reset address
        mBluetooth.close();//close Connection
        Connect.setText(R.string.connect_btn);
        setButtonStatus(false); //deactivate buttons

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Helper.REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    mBluetoothName = data.getExtras().getString(Helper.EXTRA_BLUETOOTH_NAME);
                    mBluetoothAddress = data.getExtras().getString(Helper.EXTRA_BLUETOOTH_ADDRESS);
                    // setBluetoothInfo();
                    showToast(R.string.connectedDevice + mBluetoothName);

                    if (!mBluetoothAddress.equals("")) {
                        if (!mBluetooth.connect(mBluetoothAddress)) {
                            setButtonStatus(false); //activate all buttons
                        } else {
                            Connect.setText(R.string.connSucceed);
                            setButtonStatus(true); //activate all buttons
                        }
                    }
                }
                break;
        }
    }
    private void setButtonStatus(boolean status){
        Create.setEnabled(status);

    }
}