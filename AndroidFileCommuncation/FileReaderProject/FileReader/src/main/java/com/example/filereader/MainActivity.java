package com.example.filereader;

import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends Activity {


    private EditText fileName;
    private TextView displayNumItens;
    private TextView displaySum;
    private File directory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Counter() {
        int n = 0;
        int sum = 0;
        String value;

        File file = new File (directory+"/AppSharelcamposbarboza/",fileName.getText().toString());
        if (!file.exists()) {
            Toast.makeText(getBaseContext(), "FILE PROBLEM", Toast.LENGTH_LONG).show();
        }
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while((value = br.readLine()) != null) {
                sum += Integer.valueOf(value);
                ++n;
            }
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayNumItens = (TextView) findViewById(R.id.numberItens);
        displaySum = (TextView) findViewById(R.id.sum);
        displayNumItens.setText(String.valueOf(n));
        displaySum.setText(String.valueOf(sum));
    }


    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.calculate:

                directory = (Environment.getExternalStorageDirectory());
                fileName = (EditText) findViewById(R.id.typeFileName);
                Counter();
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
