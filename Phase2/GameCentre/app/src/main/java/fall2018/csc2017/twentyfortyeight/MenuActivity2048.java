package fall2018.csc2017.twentyfortyeight;

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

import fall2018.csc2017.Interfaces.CurrentGameConstants;
import fall2018.csc2017.PersonalScoreBoardActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.ScoreBoardActivity;

public class MenuActivity2048 extends AppCompatActivity implements CurrentGameConstants {
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp_2048.ser";

    private BoardManager2048 boardManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new BoardManager2048();
        setContentView(R.layout.activity_2048_menu);
        listenAndSwitchNewGame();
        listenAndLoadGame();
        addScoreButtonListener();
        addPersonalScoreButtonListener();
    }

    //TODO: this method is not being used right now.
    void addButtonListener(int id, Class c) {
        Button button = findViewById(id);
        final Class switchTo = c;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(switchTo);
            }
        });
    }

    private void switchToActivity(Class c) {
        Intent tmp = new Intent(this, c);
        tempSaveToFile();
        if(c == PersonalScoreBoardActivity.class || c ==  ScoreBoardActivity.class){
        tmp.putExtra("highToLow", false);
        tmp.putExtra("currentGame", TWENTYFORTYEIGHT);
        }
        startActivity(tmp);
    }

    void listenAndSwitchNewGame() {
        Button button = findViewById(R.id.newButton2048);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                boardManager = new BoardManager2048();
                switchToActivity(GameActivity2048.class);
            }
        });
    }

    void listenAndLoadGame() {
       // final String temp = fileName;
        Button loadButton = findViewById(R.id.loadButton2048);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempLoadFromFile();
                if (boardManager != null) {
                    tempSaveToFile();
                    makeToast("Successfully Loaded Game");
                    //GameActivity.instance.finish();
                    //finish();
                    switchToActivity(GameActivity2048.class);
                } else {
                    makeToast("Empty Load! Save something first");
                }
            }
        });
    }

    void addScoreButtonListener(){
        Button leadButton = findViewById(R.id.leaderBoard);
        leadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(ScoreBoardActivity.class);
            }
        });
    }

    void addPersonalScoreButtonListener(){
        Button leadButton = findViewById(R.id.personalLeader);
        leadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(PersonalScoreBoardActivity.class);
            }
        });
    }

    private void makeToast(String s) {
        Toast.makeText(this,s ,Toast.LENGTH_SHORT ).show();
    }

    /**
     * Save the board manager to the temp save fileName.
     */
    public void tempSaveToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(MenuActivity2048.TEMP_SAVE_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the board manager from the temp save fileName.
     */
    private void tempLoadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(MenuActivity2048.TEMP_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager2048) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }
}
