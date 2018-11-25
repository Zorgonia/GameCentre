package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import fall2018.csc2017.Account;
import fall2018.csc2017.Interfaces.AccountConstants;

import static fall2018.csc2017.AccountActivity.findAccount;

public class SignUpActivity extends AppCompatActivity implements Serializable, AccountConstants {

    /**
     * A static ArrayList of all accounts, and its save file.
     */
    private static ArrayList<Account> allAccounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        readAllAccountFile(ACCOUNT_FILENAME);
        addSignUpButtonListener();
    }

    private void addSignUpButtonListener() {
        Button saveButton = findViewById(R.id.signUpButton);
        final EditText Username = findViewById(R.id.signUpName);
        final EditText Password = findViewById(R.id.signUpPass);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Username.getText().toString();
                String pass = Password.getText().toString();
                signIn(user, pass);
            }
        });
    }

    public void signIn(String user, String pass){
        if (user.equals("") || pass.equals("")) {
            makeToast("Credentials Incomplete!");
        } else if (findAccount(user, allAccounts) == null) {
            allAccounts.add(new Account(user, pass));
            saveNewAccount(ACCOUNT_FILENAME, allAccounts);
            makeToast("User Created! Press back and sign in");
        } else {
            makeToast("Username Taken!");
        }
    }


    /**
     * Pop a toast when called
     * @param text The text that the toast will pop
     */

    private void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Save new ArrayList of accounts with Account a added to it, provided the username
     * of a does not match the username of any other account in allAccounts.
     * @param fileName name of file to save to
     * @param accList list of accounts to save
     */
    public void saveNewAccount(String fileName, ArrayList<Account> accList) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(accList);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Attempt to load data for allAccounts from file.
     */
    public void readAllAccountFile(String file) {
        try {
            InputStream inputStream = this.openFileInput(file);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                allAccounts = (ArrayList<Account>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            writeDefaultAllAccounts(file);
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }
    /**
     * method to handle situation where when allAccounts file doesn't exist because
     * of app's very first run on device
     */
    public void writeDefaultAllAccounts(String file){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(file, MODE_PRIVATE));
            ArrayList<Account> defaultAllAccounts = new ArrayList<>();
            outputStream.writeObject(defaultAllAccounts);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
