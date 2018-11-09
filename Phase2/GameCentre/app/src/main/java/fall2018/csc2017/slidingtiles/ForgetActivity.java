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
import java.util.ArrayList;

/**
 * The activity for forgot password.
 */
public class ForgetActivity extends AppCompatActivity {

    /**
     * An ArrayList for storing all accounts
     */
    private static ArrayList<Account> allAccounts = new ArrayList<>();

    /**
     * The account to potentially give a new password.
     */
    private static Account account;

    /**
     * The save file for the account
     */
    public static final String ACCOUNT_FILENAME = "account_file.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_);
        readFiles();

        addFindPasswordButtonListener();
    }

    /**
     * Button Listener for updating the password
     */
    private void addFindPasswordButtonListener() {
        Button change = findViewById(R.id.changePassword);
        final EditText username = findViewById(R.id.userText);
        final EditText level = findViewById(R.id.levelText);
        final EditText newPassword = findViewById(R.id.newPassText);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = AccountActivity.findAccount(username.getText().toString());
                if (username.getText().toString().equals("") ||
                        level.getText().toString().equals("") ||
                        newPassword.getText().toString().equals("")) {
                    makeToast("Enter Valid Username, Level, and password");
                } else if (account != null) {
                    if (level.getText().toString().equals(Integer.toString(account.getLevel()))) {
                        updatePassword(newPassword.getText().toString());
                        makeToast("Correct Level! Your password has been Updated");
                    } else {
                        makeToast("Please Enter the correct level");
                    }
                } else {
                    makeToast("Invalid Account!");
                }
            }
        });
    }

    /**
     * Reading all the account files
     */
    private void readFiles() {
        try {
            InputStream inputStream1 = this.openFileInput(ACCOUNT_FILENAME);
            if (inputStream1 != null) {
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream1);
                ForgetActivity.allAccounts = (ArrayList<Account>) objectInputStream.readObject();
                inputStream1.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Forget Activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Forget Activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Forget Activity", "File contained unexpected data type: "
                    + e.toString());
        }
    }

    /**
     * Method for making a toast
     *
     * @param text text to be showed in toast
     */
    private void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Updates the account's password
     *
     * @param newPass new password
     */
    private void updatePassword(String newPass) {
        String name = account.getUsername();
        account.setPassword(newPass);
        for (Account acc : allAccounts) {
            if (acc.getUsername().equals(name)) {
                allAccounts.remove(acc);
                break;
            }
        }
        allAccounts.add(account);
        try {
            ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(
                    this.openFileOutput(ACCOUNT_FILENAME, MODE_PRIVATE));
            objectOutputStream1.writeObject(allAccounts);
            objectOutputStream1.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
