package fall2018.csc2017.slidingtiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.Account;
import fall2018.csc2017.AccountActivity;
import fall2018.csc2017.Interfaces.AccountConstants;
import fall2018.csc2017.LoadActivity;
import fall2018.csc2017.SaveActivity;

/**
 * The game activity.
 * TODO: If you repeatedly load saves inside the game screen, pressing the built in back button
 * will alternate you between the game screen and the load screen (add a back button m
 */
public class GameActivity extends AppCompatActivity implements Observer, AccountConstants {

    /**
     * Current user's account, and the list of accounts
     */
    private Account account;
    private ArrayList<Account> allAccounts = new ArrayList<>();

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Constants for exp on move and on finishing the game.
     */
    private static final int MOVE_EXP = 5;
    private static final int FINISH_EXP = 50;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     * Display is called everytime the tile positions change, thus
     * also increases the score
     */
    public void display() {
        if (boardManager.getBoardStatus()) {
            boardManager.getBoardScore().increaseScore();
            updateTileButtons();
            TextView scoreDisplay = findViewById(R.id.ScoreDisplay);
            scoreDisplay.setText(String.format("Score: %s", String.valueOf(boardManager.getBoardScore().getScoreValue())));
            gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
            saveFinalScore();
            account.increaseExperience(MOVE_EXP);
            writeAccountFile();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main_);
        readFiles();
        addUndoButtonListener();
        addSaveButtonListener();
        addLoadButtonListener();
        boardManager.getBoardScore().decreaseScore();

        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getNumCols());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / (boardManager.getComplex() + 3);
                        columnHeight = displayHeight / (boardManager.getComplex() + 3);
                        display();
                    }
                });
    }

    /**
     * Display that the board is finished
     */
    private void makeToastFinishedText() {
        Toast.makeText(this, "You can't save a finished game!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != board.getNumRows(); row++) {
            for (int col = 0; col != board.getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTileAt(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / board.getNumRows();
            int col = nextPos % board.getNumCols();
            b.setBackgroundResource(board.getTileAt(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Activates the Undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager.undo();
            }
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
                if (boardManager.getBoardStatus()) {
                    switchToSaveActivity();
                } else {
                    makeToastFinishedText();
                }
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
        });
    }

    /**
     * Switch to the save activity.
     */
    private void switchToSaveActivity() {
        Intent tmp = new Intent(this, SaveActivity.class);
        tmp.putExtra("currentGame","_sliding_tiles" );
        startActivity(tmp);
    }

    /**
     * Switch to the load activity.
     */
    private void switchToLoadActivity() {
        Intent tmp = new Intent(this, LoadActivity.class);
        tmp.putExtra("currentGame","_sliding_tiles" );
        finish();
        startActivity(tmp);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
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

    /**
     * reads from user's account file and allAccount file
     * and sets values for account and allAccounts
     */
    private void readFiles() {
        try {
            InputStream inputStream1 = this.openFileInput(SINGLE_ACC_FILE);
            if (inputStream1 != null) {
                ObjectInputStream objectInputStream1 = new ObjectInputStream(inputStream1);
                account = (Account) objectInputStream1.readObject();
                inputStream1.close();
            }
            InputStream inputStream2 = this.openFileInput(ACCOUNT_FILENAME);
            if (inputStream2 != null) {
                ObjectInputStream objectInputStream2 = new ObjectInputStream(inputStream2);
                allAccounts = (ArrayList<Account>) objectInputStream2.readObject();
                inputStream2.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Game Activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Game Activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Game Activity", "File contained unexpected data type: "
                    + e.toString());
        }
    }

    /**
     * Saves final scores to user's Account file and updates allAccount file
     * when puzzle is solved
     */
    private void saveFinalScore() {
        if (boardManager.gameFinished()) {
            boardManager.setBoardToInactive();

            // updates account's score/exp and then updates it to allAccounts
            account.increaseExperience(FINISH_EXP);
            account.updateHighScores("Sliding Tiles",boardManager.getBoardScore());
            writeAccountFile();
        } else {
            // Auto save if the game is not finished.
            saveToFile("save_auto" + "_sliding_tiles" + AccountActivity.username + ".ser");
        }
    }

    /**
     * Writes new data for account to file
     */
    private void writeAccountFile() {
        updateAllAccountList();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    this.openFileOutput(SINGLE_ACC_FILE, MODE_PRIVATE));
            ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(
                    this.openFileOutput(ACCOUNT_FILENAME, MODE_PRIVATE));
            objectOutputStream.writeObject(account);
            objectOutputStream.close();
            objectOutputStream1.writeObject(allAccounts);
            objectOutputStream1.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * the list of all accounts updated to be saved
     */
    private void updateAllAccountList(){
        String name = account.getUsername();
        for (Account acc : allAccounts) {
            if (acc.getUsername().equals(name)) {
                allAccounts.remove(acc);
                break;
            }
        }
        allAccounts.add(account);
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}