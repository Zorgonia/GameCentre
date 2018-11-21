package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

import fall2018.csc2017.GameSelectActivity;

public class AccountActivity extends AppCompatActivity implements Serializable {
    /**
     * A static ArrayList of all accounts, and its save file.
     */
    public static String username;
    private static ArrayList<Account> allAccounts = new ArrayList<>();
    public static final String ACCOUNT_FILENAME = "account_file.ser";
    public static final String SINGLE_ACC_FILE = "account_single.ser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readAllAccountFile();
        setContentView(R.layout.activity_account_);
        addSignInButtonListener();
        addForgetButtonListener();
        addSignUpButtonListener();
    }

    /**
     * Adds a listener for the sign up button
     */
    private void addSignUpButtonListener() {
        Button signUp = findViewById(R.id.SwitchToSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });
    }

    /**
     * Switches the screen to sign up
     */
    private void switchToSignUp() {
        Intent tmp = new Intent(this, SignUpActivity.class);
        startActivity(tmp);
    }

    private void addForgetButtonListener(){
        Button forget = findViewById(R.id.forgetButton);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToForgetPassword();
            }
        });
    }

    private void switchToForgetPassword() {
        Intent tmp = new Intent(this, ForgetActivity.class);
        startActivity(tmp);
    }

    private void addSignInButtonListener() {
        Button saveButton = findViewById(R.id.SignInButton);
        final EditText Username = findViewById(R.id.LoginName);
        final EditText Password = findViewById(R.id.LoginPass);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account a = findAccount(Username.getText().toString(), allAccounts);
                username = Username.getText().toString();
                if (username.equals("") ||
                        Password.getText().toString().equals("")){
                    makeToast("Enter Valid Username and Password");
                }
                else if (a != null){
                    if (!a.authenticate(Password.getText().toString())){
                        makeToast("Incorrect Password");
                    }
                    else {
                        saveSignedInAccount(a);
                        makeToast("Successfully Signed In");
                        switchToStartingActivity();
                    }
                }else{
                    makeToast("Invalid Account!");
                }
            }
        });
    }

    /**
     * Pop a toast when called
     * @param text The text that the toast will pop
     */

    private void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Updates all account from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        readAllAccountFile();
    }

    private void switchToStartingActivity() {
        Intent tmp = new Intent(this, GameSelectActivity.class);
        startActivity(tmp);
    }

    /**
     * Find the account in the list allAccounts that matches the given username.
     *
     * @param name username to search for
     * @return the account with a matching username if one exists, null otherwise.
     */
    public static Account findAccount(String name, ArrayList<Account> accList) {
        for (Account a : accList) {
            if (a.getUsername().equals(name)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Attempt to load data for allAccounts from file.
     */
    private void readAllAccountFile() {
        try {
            InputStream inputStream = this.openFileInput(ACCOUNT_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                AccountActivity.allAccounts = (ArrayList<Account>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            writeDefaultAllAccounts();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * saves signed in account to SINGLE_ACC_FILE for later use in app
     * called by addSignInButtonListener
     * @param signedInAccount: Account to be saved
     */
    private void saveSignedInAccount(Account signedInAccount){
        try {
            ObjectOutputStream outputStream2 = new ObjectOutputStream(
                    this.openFileOutput(SINGLE_ACC_FILE, MODE_PRIVATE));
            outputStream2.writeObject(signedInAccount);
            outputStream2.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * method to handle situation where when allAccounts file doesn't exist because
     * of app's very first run on device
     */
    private void writeDefaultAllAccounts(){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(ACCOUNT_FILENAME, MODE_PRIVATE));
            ArrayList<Account> defaultAllAccounts = new ArrayList<>();
            outputStream.writeObject(defaultAllAccounts);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}