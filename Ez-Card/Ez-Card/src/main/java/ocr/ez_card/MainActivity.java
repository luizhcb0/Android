package ocr.ez_card;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {

    private String dirName = "EZPictures";
    private String dirObjName = "EZCards";
    private File directoryPic;
    private File directoryObj;
    private String fileName = "/example.jpg";
    private Uri outputFileUri;

    private static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        directoryPic = createDirPic();
        File file = new File(directoryPic.getPath()+fileName);
        outputFileUri = Uri.fromFile( file );

        //directoryObj = createDirObj();
        //File file = new File(directoryObj.getPath()+fileName);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void turnOnCamera(View v) {
        //Intent intent = new Intent(this, CameraActivity.class);
        //startActivity(intent);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );

        startActivityForResult(intent,REQUEST_CAMERA);
    }

    public void cardsView (View v) {
        Intent intent = new Intent(this,CardActivity.class);
        startActivity(intent);

    }

    public void listCards (View v) {
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public File createDirPic() {
        File directory = Environment.getExternalStorageDirectory();
        File file = new File(directory, dirName);
        if (!file.mkdirs()) {
            Log.e("FileCreator", "Directory not created");
        }
        return file;
    }

    public File createDirObj() {
        File directory = Environment.getExternalStorageDirectory();
        File file = new File(directory, dirObjName);
        if (!file.mkdirs()) {
            Log.e("FileCreator", "Directory not created");
        }
        return file;
    }

    //Get the result of activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CAMERA :
                if (resultCode == Activity.RESULT_OK) {
                    //Call this function to begin the BardActivity and be able to work with the arduino
                    //this.connectDevice(data);
                }
                break;
            //case REQUEST_ENABLE_BT:
              //  Toast.makeText(this, "Bluetooth ON", Toast.LENGTH_SHORT).show();
                //break;
        }
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState){
        Log.i( "MakeMachine", "onRestoreInstanceState()");
        if(savedInstanceState.getBoolean(CameraActivity.PHOTO_TAKEN)) {
            //onPhotoTaken();
        }
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        //outState.putBoolean(CameraActivity.PHOTO_TAKEN, _taken );
    }

}
