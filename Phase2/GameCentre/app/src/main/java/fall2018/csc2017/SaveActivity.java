package fall2018.csc2017;

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
import fall2018.csc2017.Interfaces.CurrentGameConstants;
import fall2018.csc2017.Interfaces.TappableManager;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SlidingTilesMenuActivity;
import fall2018.csc2017.twentyfortyeight.MenuActivity2048;

/**
 * A save activity
 */
public class SaveActivity extends AppCompatActivity implements CurrentGameConstants {


    /**
     * The main save file.
     */
    private String username;
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";
    /**
     * The board manager.
     */
    private TappableManager boardManager;
    /**
     * The current game by CurrentGameConstants
     */
    private int currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentGame = getIntent().getIntExtra("currentGame", 0);
        loadGameBoard();
        saveToFile(TEMP_SAVE_FILENAME);
        username = AccountActivity.username;

        setContentView(R.layout.activity_save_);
        addSaveButtonListener("save_file1" + currentGame + username + ".ser", R.id.SaveButton1);
        addSaveButtonListener("save_file2" + currentGame + username + ".ser", R.id.SaveButton2);
        addSaveButtonListener("save_file3" + currentGame + username + ".ser", R.id.SaveButton3);

    }

    /**
     * Loads the right game file corresponding to the current game.
     */
    private void loadGameBoard() {
        if (currentGame == SLIDING_TILES) {
            loadFromFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        } else if (currentGame == TWENTYFORTYEIGHT) {
            loadFromFile(MenuActivity2048.TEMP_SAVE_FILENAME);
        }
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener(String fileName, int id) {
        final String saveFile = fileName;
        Button saveButton = findViewById(id);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(saveFile);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Saved Game", Toast.LENGTH_SHORT).show();
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
