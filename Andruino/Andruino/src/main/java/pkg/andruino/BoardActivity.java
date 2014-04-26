package pkg.andruino;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class BoardActivity extends Activity {

    BTConnection conn = null;
    Button button;
    Button aux;
    boolean blink, r1, y1, g1;
    String ron = "1"; //Red On
    String roff = "2"; //Red Off
    String yon = "3"; //Yellow On
    String yoff = "4"; //Yellow Off
    String gon = "5"; //Green On
    String goff = "6"; //Grenn Off
    String rblink = "7"; //Red Blink
    String yblink = "8"; //Yellow Blink
    String gblink = "9"; //Green Blink
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        String add = getIntent().getExtras().getString("address");

        Toast.makeText(this, "Ready", Toast.LENGTH_SHORT).show();


        conn = new BTConnection(add);
        // Attempt to connect to the device
        conn.setActivity(this);
        blink = false;
        res = getResources();

    }

    public void onClick (View v) throws IOException {

        switch (v.getId()) {
            case R.id.btn1_red:

                if (r1) {
                    r1 = false;
                    button = (Button) findViewById(R.id.btn1_red);
                    button.setBackgroundResource(R.color.red_off);
                    button.setText("Off");
                    conn.sendData(roff);
                }
                else {
                    r1 = true;
                    button = (Button) findViewById(R.id.btn1_red);
                    button.setBackgroundResource(R.color.red_on);
                    button.setText("On");
                    if (blink) {
                        conn.sendData(rblink);
                    }
                    else {
                        conn.sendData(ron);
                    }
                }

                break;

            case R.id.btn1_yellow:

                if (y1) {
                    y1 = false;
                    button = (Button) findViewById(R.id.btn1_yellow);
                    button.setBackgroundResource(R.color.yellow_off);
                    button.setText("Off");
                    conn.sendData(yoff);
                }
                else {
                    y1 = true;
                    button = (Button) findViewById(R.id.btn1_yellow);
                    button.setBackgroundResource(R.color.yellow_on);
                    button.setText("On");
                    if (blink) {
                        conn.sendData(yblink);
                    }
                    else {
                        conn.sendData(yon);
                    }
                }

                break;

            case R.id.btn1_green:

                if (g1) {
                    g1 = false;
                    button = (Button) findViewById(R.id.btn1_green);
                    button.setBackgroundResource(R.color.green_off);
                    button.setText("Off");
                    conn.sendData(goff);
                }
                else {
                    g1 = true;
                    button = (Button) findViewById(R.id.btn1_green);
                    button.setBackgroundResource(R.color.green_on);
                    button.setText("On");
                    if (blink) {
                        conn.sendData(gblink);
                    }
                    else {
                        conn.sendData(gon);
                    }

                }

                break;

            case R.id.btn_blink:
                blink = true;
                button = (Button) findViewById(R.id.btn_blink);
                button.setBackgroundResource(R.color.blue);
                button.setTextColor(res.getColor(R.color.white));
                aux = (Button) findViewById(R.id.btn_turnOnOff);
                aux.setBackgroundResource(R.color.white);
                aux.setTextColor(res.getColor(R.color.black));
                break;

            case R.id.btn_turnOnOff:
                blink = false;
                button = (Button) findViewById(R.id.btn_turnOnOff);
                button.setBackgroundResource(R.color.blue);
                button.setTextColor(res.getColor(R.color.white));
                aux = (Button) findViewById(R.id.btn_blink);
                aux.setBackgroundResource(R.color.white);
                aux.setTextColor(res.getColor(R.color.black));

                break;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.board, menu);
        return true;
    }


}
