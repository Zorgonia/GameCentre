package fall2018.csc2017.slidingtiles;

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

import fall2018.csc2017.AccountActivity;
import fall2018.csc2017.Interfaces.TappableManager;
import fall2018.csc2017.twentyfortyeight.GameActivity2048;
import fall2018.csc2017.twentyfortyeight.MenuActivity2048;

public class LoadActivity extends AppCompatActivity {


    /**
     * The main save file.
     */
    private String username = AccountActivity.username;
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";
    /**
     * The board manager.
     */
    private TappableManager boardManager;
    private String currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentGame = getIntent().getStringExtra("currentGame");
        loadGameBoard();
        saveToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.activity_load_);
        addLoadButtonListener("save_file1" + currentGame + username + ".ser", R.id.LoadButton1);
        addLoadButtonListener("save_file2" + currentGame + username + ".ser", R.id.LoadButton2);
        addLoadButtonListener("save_file3" + currentGame + username + ".ser", R.id.LoadButton3);

        // This save has your SECOND MOST RECENT move done. We don't want to save a finished game
        addLoadButtonListener("save_auto" + currentGame + username + ".ser", R.id.AutoLoadButton);

    }

    /**
     * Loads the right game file corresponding to the current game.
     * //TODO put some error checking in.
     */
    private void loadGameBoard() {
        if (currentGame.equals("_sliding_tiles")) {
            loadFromFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        } else if (currentGame.equals("2048")) {
            loadFromFile(MenuActivity2048.TEMP_SAVE_FILENAME);
        }
    }

    /**
     * Activates load button functionality.
     */
    private void addLoadButtonListener(String fileName, int id) {
        final String temp = fileName;
        Button loadButton = findViewById(id);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(temp);
                if (boardManager != null) {
                    saveToFile(TEMP_SAVE_FILENAME);
                    makeToast("Successfully Loaded Game");
                    //TODO: I commented this out because it didn't work fluidly with 2048 but I noticed something while doing that
                    //Not sure if this exactly was the cause, but saves wouldn't work properly if you closed the app
                    //Saves currently work as intended (as in they carry on if you turn off the device) needs more testing
//                    GameActivity.instance.finish();
                    //              finish();
                    switchToGame();
                } else {
                    makeToast("Empty Load! Save something first");
                }
            }
        });
    }

    /**
     * Provides a popup that load was successful.
     */
    private void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);

        if (currentGame.equals("_sliding_tiles")) {
            tmp = new Intent(this, GameActivity.class);
            saveToFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        } else if (currentGame.equals("2048")) {
            tmp = new Intent(this, GameActivity2048.class);
            saveToFile(MenuActivity2048.TEMP_SAVE_FILENAME);
        }

        startActivity(tmp);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (TappableManager) input.readObject();
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

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
