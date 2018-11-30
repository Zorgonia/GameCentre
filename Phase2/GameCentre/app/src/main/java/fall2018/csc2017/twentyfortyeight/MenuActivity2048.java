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
import fall2018.csc2017.LoadActivity;
import fall2018.csc2017.PersonalScoreBoardActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.ScoreBoardActivity;

/**
 * The main menu activity for 2048
 */
public class MenuActivity2048 extends AppCompatActivity implements CurrentGameConstants {
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp_2048.ser";

    /**
     * The boardManager to use when new game is pressed
     */
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

    /**
     * Switches to activity with class c
     * @param c the class of the activity to switch to
     */
    private void switchToActivity(Class c) {
        Intent tmp = new Intent(this, c);
        tempSaveToFile();
        if (c == PersonalScoreBoardActivity.class || c == ScoreBoardActivity.class) {
            tmp.putExtra("highToLow", false);
            tmp.putExtra("currentGame", TWENTYFORTYEIGHT);
        } else if(c == LoadActivity.class) {
            tmp.putExtra("currentGame",TWENTYFORTYEIGHT);
        }
        startActivity(tmp);
    }

    /**
     * Add the new button listener and call the switchToActivity method on GameActivity2048
     */
    void listenAndSwitchNewGame() {
        Button button = findViewById(R.id.newButton2048);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boardManager = new BoardManager2048();
                switchToActivity(GameActivity2048.class);
            }
        });
    }

    /**
     * Add a listener for the load button and call switchToActivity on LoadActivity.class
     */
    void listenAndLoadGame() {
        Button loadButton = findViewById(R.id.loadButton2048);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(LoadActivity.class);
            }
        });
    }

    /**
     * Add a scoreboard (general) button listener
     */
    void addScoreButtonListener() {
        Button leadButton = findViewById(R.id.leaderBoard);
        leadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(ScoreBoardActivity.class);
            }
        });
    }

    /**
     * Add a personal scoreboard button listener
     */
    void addPersonalScoreButtonListener() {
        Button leadButton = findViewById(R.id.personalLeader);
        leadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(PersonalScoreBoardActivity.class);
            }
        });
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

}
