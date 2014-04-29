package ocr.ez_card;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class CardActivity extends ActionBarActivity {

    EditText nameInput;
    EditText emailInput;
    EditText addressInput;
    EditText phoneInput;
    EditText faxInput;

    String nameString;
    String emailString;
    String addressString;
    String phoneString;
    String faxString;

    FileOutputStream stream;
    String FILE_NAME = "Card_Objects";
    String KEY = "ContactsFile";
    String TAG = "contacts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.card, menu);
        return true;
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

    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void saveCard() {
        nameInput = (EditText) findViewById(R.id.nameInput);
        emailInput = (EditText) findViewById(R.id.emailInput);
        addressInput = (EditText) findViewById(R.id.addressInput);
        phoneInput = (EditText) findViewById(R.id.phoneInput);
        faxInput = (EditText) findViewById(R.id.faxInput);

        nameString = nameInput.getText().toString();
        emailString = emailInput.getText().toString();
        addressString = addressInput.getText().toString();
        phoneString = phoneInput.getText().toString();
        faxString = faxInput.getText().toString();

        Contact c = new Contact(nameString, emailString, addressString, phoneString, faxString);

        List<Contact> contacts = new ArrayList<Contact>();

        Log.e(TAG, "1");
        contacts.add(c);

        try {
            // Save the list of entries to internal storage
            InternalStorage.writeObject(this, KEY, contacts);

            Log.e(TAG, "2");
            // Retrieve the list from internal storage
            List<Contact> cachedContacts = (List<Contact>) InternalStorage.readObject(this, KEY);

            // Display the items from the list retrieved.
            for (Contact entry : cachedContacts) {
                Log.d(TAG, entry.getName());
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }



    }

}
