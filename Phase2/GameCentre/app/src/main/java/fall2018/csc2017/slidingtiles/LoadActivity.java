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
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(StartingActivity.TEMP_SAVE_FILENAME);
        saveToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.activity_load_);
        addLoadButtonListener("save_file1" + username + ".ser", R.id.LoadButton1);
        addLoadButtonListener("save_file2" + username + ".ser", R.id.LoadButton2);
        addLoadButtonListener("save_file3" + username + ".ser", R.id.LoadButton3);

        // This save has your SECOND MOST RECENT move done. We don't want to save a finished game
        addLoadButtonListener("save_auto" + username + ".ser", R.id.AutoLoadButton);

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
                if (boardManager.getBoard() != null) {
                    saveToFile(TEMP_SAVE_FILENAME);
                    makeToast("Successfully Loaded Game");
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
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
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
                boardManager = (BoardManager) input.readObject();
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
