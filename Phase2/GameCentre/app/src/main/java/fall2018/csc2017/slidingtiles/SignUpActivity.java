package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static fall2018.csc2017.slidingtiles.AccountActivity.findAccount;

public class SignUpActivity extends AppCompatActivity implements Serializable {

    /**
     * A static ArrayList of all accounts, and its save file.
     */
    private static ArrayList<Account> allAccounts = new ArrayList<>();
    public static final String ACCOUNT_FILENAME = "account_file.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        addSignUpButtonListener();
    }

    private void addSignUpButtonListener() {
        Button saveButton = findViewById(R.id.signUpButton);
        final EditText Username = findViewById(R.id.signUpName);
        final EditText Password = findViewById(R.id.signUpPass);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Username.getText().toString().equals("") ||
                        Password.getText().toString().equals("")){
                    makeToast("Credentials Incomplete!");
                }
                else if (findAccount(Username.getText().toString()) == null){
                    saveNewAccount(new Account(Username.getText().toString(),
                            Password.getText().toString()));
                    makeToast("User Created! Press back and sign in");
                }else{
                    makeToast("Username Taken!");
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
     * Save new ArrayList of accounts with Account a added to it, provided the username
     * of a does not match the username of any other account in allAccounts.
     * @param a new account to be added to ArrayList allAccounts
     */
    public void saveNewAccount(Account a) {
        allAccounts.add(a);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(ACCOUNT_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(allAccounts);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
