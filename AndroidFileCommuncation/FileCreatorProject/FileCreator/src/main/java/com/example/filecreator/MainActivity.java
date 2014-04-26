package com.example.filecreator;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends Activity {

    private String dirName = "AppSharelcamposbarboza";
    private EditText number;
    private EditText fileName;
    private File directory;
    private TextView displayNumItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File createDir() {
        File directory = Environment.getExternalStorageDirectory();
        File file = new File(directory, dirName);
        if (!file.mkdirs()) {
            Log.e("FileCreator", "Directory not created");
        }
        return file;
    }

    public void Counter() {
        int n = 0;
        File file = new File (directory.getPath()+"/"+fileName.getText().toString());
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while(br.readLine() != null) {
                ++n;
            }
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayNumItens = (TextView) findViewById(R.id.numberItens);
        displayNumItens.setText(String.valueOf(n));
        number.setText("");
        number.setHint("Type a Number");

    }


    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.addNumberButton:

                fileName = (EditText) findViewById(R.id.typeFileName);
                number = (EditText) findViewById(R.id.typeNumber);
                int inputValue = Integer.parseInt(number.getText().toString());

                directory = createDir();

                if ((number.getText().length() == 0)||(number.getText().length() > 10)) {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show();
                    return;
                }
                WriteInFile(inputValue);
                Counter();
                break;
        }
    }

    public void WriteInFile (int inputValue) {
        boolean exist;

        File file = new File (directory.getPath()+"/"+fileName.getText().toString());
        FileWriter fw;
        exist = file.exists();

        try {
            if (exist) {
                fw = new FileWriter(file, true);
            } else {
                fw = new FileWriter(file);
            }
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(String.valueOf(inputValue));
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
