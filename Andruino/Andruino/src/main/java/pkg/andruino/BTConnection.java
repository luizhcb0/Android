package pkg.andruino;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class BTConnection /*implements Serializable*/ {

    private BluetoothSocket socket = null;
    private BluetoothDevice btShield = null;
    private BluetoothAdapter btAdapter;
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static String TAG = "BluetoothError";
    private OutputStream outStream;
    private InputStream inStream;
    Handler handler;
    TextView Result;
    Activity activity;
    String address;

    boolean stopWorker;
    int readBufferPosition = 0;
    byte[] readBuffer = new byte[1024];
    byte delimiter = 10;

    public BTConnection(String _address) {
        address = _address;
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        btShield = btAdapter.getRemoteDevice(address);
        btAdapter.cancelDiscovery();
        outStream = null;
        inStream = null;
        stopWorker = false;
        handler = new Handler();


        try {
            socket = btShield.createRfcommSocketToServiceRecord(uuid);
            socket.connect();
            //this.listenForData();
        }
        catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e2) {
                Log.d(TAG, "Unable to end the connection");
            }
            Log.d(TAG, "Socket creation failed");
            e.printStackTrace();
        }
    }


    public void sendData (String message) {
        byte[] msgBuffer = message.getBytes();

        Log.d(TAG, "...Send data: " + message + "...");

        try {
            outStream = socket.getOutputStream();
            Log.d(TAG, "SENT: " + message);
        } catch (IOException e) {
            Log.d(TAG, "Bug BEFORE Sending", e);
        }

        try {
            outStream.write(msgBuffer);
            Log.d(TAG, "SENT2: " + message);
        } catch (IOException e) {
            String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
            msg = msg +  ".\n\nCheck that the SPP UUID: " + uuid.toString() + " exists on server.\n\n";
            Log.d("Error_BT","Error");
        }
    }


    public void listenForData() {
        try {
            inStream = socket.getInputStream();
        }
        catch (IOException e) {

        }

        Thread workerThread = new Thread(new Runnable() {
            public void run() {
                while(!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = inStream.available();
                        if(bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            inStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++) {
                                byte b = packetBytes[i];
                                if(b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    handler.post(new Runnable() {
                                        public void run() {
                                            if(Result.getText().toString().equals("..")) {
                                                Result.setText(data);
                                            }
                                            else {
                                                Result.append("\n"+data);
                                            }

                                                        /* You also can use Result.setText(data); it won't display multilines
                                                        */

                                        }
                                    });
                                }
                                else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });
        workerThread.start();
    }


    public BluetoothSocket getSocket() {
        return socket;
    }

    public BluetoothDevice getDevice() {
        return btShield;
    }

    public void setActivity (Activity _activity) {
        activity = _activity;
        Result = (TextView) activity.findViewById(R.id.result);
    }

    public String getAddress () {
        return btShield.getAddress();
    }

    public void cancel() {
        try {
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
