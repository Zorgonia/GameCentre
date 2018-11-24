package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";
    /**
     * The board manager.
     */
    private BoardManager boardManager = null;

    int move = 0;

    /**
     * Amount by which the default limit of moves of the undo function changes for a new game
     */
    int undoLimitChange = 0;

    boolean undoLimited = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        boardManager = new BoardManager();
        tempSaveToFile();

        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addHighScoresButtonListener();
        addPersonalScoresButtonListener();
        addComplexitySeeker();
        addUndoSubtractButtonListener();
        addUndoAddButtonListener();
        addToggleUndoLimitButtonListener();

    }

    /**
     * Activates the Leaderboards Button
     */
    private void addHighScoresButtonListener() {
        Button highScoresButton = findViewById(R.id.ScoreButton);
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoreBoardActivity.highToLow = false;
                switchToScoreBoardActivity();
            }
        });
    }

    /**
     * Activates the Personal Bests button
     */
    private void addPersonalScoresButtonListener() {
        Button personalScoresButton = findViewById(R.id.PersonalScoreButton);
        personalScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalScoreBoardActivity.highToLow = false;
                switchToPersonalScoresActivity();
            }
        });
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(move);
//                boardManager.setComplexity(move);
                boardManager.incrementUndo(undoLimitChange);
                boardManager.setLimited(undoLimited);
                boardManager.refreshBoard();
                switchToGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLoadActivity();
            }
//                loadFromFile(SAVE_FILENAME);
//                saveToFile(TEMP_SAVE_FILENAME);
//                makeToastLoadedText();
//            }
        });
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempLoadFromFile();
                if (boardManager != null) {
                    switchToSaveActivity();
                } else {
                    makeToast("You have no active games right now!");
                }
            }
        });
    }

    /**
     * Allows the seek bar to be dragged and adjust complexity of board.
     */
    private void addComplexitySeeker() {
        SeekBar bar = findViewById(R.id.seekBar2);
        final TextView text = findViewById(R.id.textView);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                move = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                makeToast("Move the slider to change the complexity of the game");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                text.setText(String.format(Locale.CANADA, "Complexity will be : %d x %d", 3 + move, 3 + move));
            }
        });
    }

    /**
     * Activates the undo limit subtraction button
     */
    private void addUndoSubtractButtonListener() {
        Button undoSubtractButton = findViewById(R.id.UndoSubtractButton);
        final TextView undoText = findViewById(R.id.undoLimit);
        undoSubtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (undoLimited) {
                    incrementUndo(undoText, false);
                }
            }
        });
    }

    /**
     * Activates the undo limit addition button
     */
    private void addUndoAddButtonListener() {
        Button undoAddButton = findViewById(R.id.UndoAddButton);
        final TextView undoText = findViewById(R.id.undoLimit);
        undoAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (undoLimited) {
                    incrementUndo(undoText, true);
                }
            }
        });
    }

    /**
     * Activates the undo limit toggle button
     */
    private void addToggleUndoLimitButtonListener() {
        Button toggleUndoLimitButton = findViewById(R.id.ToggleUndoLimitButton);
        final TextView undoText = findViewById(R.id.undoLimit);
        toggleUndoLimitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleUnlimitedUndo(undoText);
            }
        });
    }

    /**
     * Update the undo limit and text displaying it
     *
     * @param undoText text displayed under the add/subtract buttons
     * @param add      if true, add to the undo limit, otherwise subtract
     */
    private void incrementUndo(TextView undoText, boolean add) {
        if (add) {
            undoLimitChange += 1;
        } else if (undoLimitChange > -2) {
            undoLimitChange -= 1;
        }
        undoText.setText(String.format(Locale.CANADA, "Undo limit will be: %d",
                3 + undoLimitChange));
    }

    /**
     * Toggle the undo limit and update the text displaying it
     *
     * @param undoText text displayed above the toggle button
     */
    private void toggleUnlimitedUndo(TextView undoText) {
        if (undoLimited) {
            undoLimited = false;
            undoText.setText(R.string.UndoUnlimited);
        } else {
            undoLimited = true;
            undoText.setText(String.format(Locale.CANADA, "Undo limit will be: %d",
                    3 + undoLimitChange));
        }

    }

    /**
     * Makes a toast about how the seek bar changes complexity.
     */
    private void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }



    /**
     * Switch the game to the load screen
     */
    private void switchToLoadActivity() {
        Intent tmp = new Intent(this, LoadActivity.class);
        tempSaveToFile();
        startActivity(tmp);
    }

    /**
     * Switch the game to the save screen
     */
    private void switchToSaveActivity() {
        Intent tmp = new Intent(this, SaveActivity.class);
        tempSaveToFile();
        startActivity(tmp);
    }

    /**
     * Switch the game to the scoreboard screen
     */
    private void switchToScoreBoardActivity() {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        tempSaveToFile();

        startActivity(tmp);
    }

    /**
     * Switch the game to the personal scoreboard screen
     */
    private void switchToPersonalScoresActivity() {
        Intent tmp = new Intent(this, PersonalScoreBoardActivity.class);
        tempSaveToFile();
        startActivity(tmp);
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        tempLoadFromFile();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        tempSaveToFile();
        startActivity(tmp);
    }


    /**
     * Load the board manager from the temp save fileName.
     */
    private void tempLoadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(StartingActivity.TEMP_SAVE_FILENAME);
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
     * Save the board manager to the temp save fileName.
     */
    public void tempSaveToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(StartingActivity.TEMP_SAVE_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
