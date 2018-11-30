package fall2018.csc2017;

import android.content.Intent;
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

import fall2018.csc2017.Interfaces.AccountConstants;
import fall2018.csc2017.slidingtiles.R;

/**
 * The login activity.
 */
public class AccountActivity extends AppCompatActivity implements Serializable, AccountConstants {
    /**
     * A static ArrayList of all accounts, and its save file.
     */
    public static String username;
    public static ArrayList<Account> allAccounts = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readAllAccountFile(ACCOUNT_FILENAME);
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

    /**
     * Add a forget password button listener
     */
    private void addForgetButtonListener(){
        Button forget = findViewById(R.id.forgetButton);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToForgetPassword();
            }
        });
    }

    /**
     * Switch to the forget password activity
     */
    private void switchToForgetPassword() {
        Intent tmp = new Intent(this, ForgetActivity.class);
        startActivity(tmp);
    }

    /**
     * Add a sign in button listener
     */
    private void addSignInButtonListener() {
        Button saveButton = findViewById(R.id.SignInButton);
        final EditText Username = findViewById(R.id.LoginName);
        final EditText Password = findViewById(R.id.LoginPass);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = Username.getText().toString();
                String pass = Password.getText().toString();
                signIn(username, pass, allAccounts);
            }

        });
    }

    /**
     * A method for checking whether a sign in is successful
     * @param user the username inputted
     * @param pass password inputted
     * @param allA all accounts file
     */
    public void signIn(String user, String pass, ArrayList<Account> allA){
        Account a = findAccount(user, allA);
        if (user.equals("") ||
                pass.equals("")){
            makeToast("Enter Valid Username and Password");
        }
        else if (a != null){
            if (!a.authenticate(pass)){
                makeToast("Incorrect Password");
            }
            else {
                saveSignedInAccount(a, SINGLE_ACC_FILE);
                makeToast("Successfully Signed In");
                switchToGameSelectActivity();
            }
        }else{
            makeToast("Invalid Account!");
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
     * Updates all account from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        readAllAccountFile(ACCOUNT_FILENAME);
    }

    /**
     * Switch to the game select activity
     */
    private void switchToGameSelectActivity() {
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
    public void readAllAccountFile(String file) {
        try {
            InputStream inputStream = this.openFileInput(file);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                AccountActivity.allAccounts = (ArrayList<Account>) input.readObject();
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
     * saves signed in account to SINGLE_ACC_FILE for later use in app
     * called by addSignInButtonListener
     * @param signedInAccount: Account to be saved
     */
    private void saveSignedInAccount(Account signedInAccount, String file){
        try {
            ObjectOutputStream outputStream2 = new ObjectOutputStream(
                    this.openFileOutput(file, MODE_PRIVATE));
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