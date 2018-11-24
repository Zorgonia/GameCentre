package fall2018.csc2017.checkers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fall2018.csc2017.slidingtiles.Account;
import fall2018.csc2017.slidingtiles.AccountActivity;
import fall2018.csc2017.slidingtiles.PersonalScoreBoardActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SaveActivity;
import fall2018.csc2017.slidingtiles.ScoreBoardActivity;

public class CheckerMenuActivity extends AppCompatActivity {
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "checker_save_file_tmp.ser";
    public static String CHECKER_SAVE_FILE;

    public static final String SINGLE_ACC_FILE = "account_single.ser";
    public static final String ACCOUNT_FILENAME = "account_file.ser";
    /**
     * A board manager that is loaded into a new game
     */
    CheckerBoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers_menu);
        addStartButtonListener();
        addLoadButtonListener();
        addHighScoresButtonListener();
        addPersonalScoresButtonListener();
        //I moved Checker_save_file to menuActivity. It is necessary here.
        CHECKER_SAVE_FILE = "checker_save_" + AccountActivity.username + ".ser";
    }

    private void addStartButtonListener(){
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new CheckerBoardManager();
                switchToGameActivity();
            }
        });

    }

    private void switchToGameActivity() {
        Intent tmp = new Intent(this, CheckerGameActivity.class);
        tempSaveToFile();
        startActivity(tmp);
    }

    private void switchToPersonalScoreBoardActivity() {
        Intent tmp = new Intent(this, PersonalScoreBoardActivity.class);
        PersonalScoreBoardActivity.highToLow = true;
        startActivity(tmp);
    }

    private void switchToScoreBoardActivity() {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        ScoreBoardActivity.highToLow = true;
        startActivity(tmp);
    }

    //TODO check that loading an empty save will prevent you from doing so
    private void addLoadButtonListener(){
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(CHECKER_SAVE_FILE);
                if (boardManager != null) {
                    switchToGameActivity();
                } else {
                    makeToast("That's an empty save. Play something first!");
                }
            }
        });

    }

    /**
     * Makes a toast about how the seek bar changes complexity.
     */
    private void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void addHighScoresButtonListener(){
        Button highScoresButton = findViewById(R.id.HighScoresButton);
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreBoardActivity();
            }
        });

    }

    private void addPersonalScoresButtonListener(){
        Button personalScoresButton = findViewById(R.id.PersonalScoresButton);
        personalScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPersonalScoreBoardActivity();
            }
        });

    }

    /**
     * Save the board manager to the temp save fileName.
     */
    public void tempSaveToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(CheckerMenuActivity.TEMP_SAVE_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void loadFromFile(String filename){
        try {
            InputStream inputStream = this.openFileInput(filename);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (CheckerBoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            boardManager = null;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

}
