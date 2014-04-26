package pkg.andruino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends Activity {

    private BluetoothAdapter bluetooth;
    private Intent intent = null;
    private static final int REQUEST_ENABLE_BT = 3;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private BluetoothDevice device = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        bluetooth = BluetoothAdapter.getDefaultAdapter();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Turning On the bluetooth of the device
    public void turnOnBluetooth (View v) {

        if (!bluetooth.isEnabled()) {
            intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        }


    }

    //Turning Off the bluetooth of the device
    public void turnOffBluetooth (View v) {

        bluetooth.disable();
        Toast.makeText(this, "Bluetooth OFF", Toast.LENGTH_SHORT).show();

    }

    //Start the Activity DiscoverActivity to search and pair devices
    public void discoverDevice (View v) {

        if (!bluetooth.isEnabled()) {
            intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        }
        else {
            intent = new Intent(this, DiscoverActivity.class);
            startActivityForResult(intent, REQUEST_CONNECT_DEVICE);
        }

    }


    //Connecting the device and starting the BoardActivity
    public void connectDevice (Intent data) {
        // Get the device MAC address
        String address = data.getExtras().getString(DiscoverActivity.EXTRA_DEVICE_ADDRESS);

        intent = new Intent(this, BoardActivity.class);
        intent.putExtra("address", address);
        startActivity(intent);
    }

    //Get the result of activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE :
                if (resultCode == Activity.RESULT_OK) {
                    //Call this function to begin the BardActivity and be able to work with the arduino
                    this.connectDevice(data);
                }
                break;
            case REQUEST_ENABLE_BT:
                Toast.makeText(this, "Bluetooth ON", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
