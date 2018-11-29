package fall2018.csc2017.twentyfortyeight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import fall2018.csc2017.ParentClasses.GameBoard;
import fall2018.csc2017.slidingtiles.CustomAdapter;
import fall2018.csc2017.LoadActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.SaveActivity;

/**
 * The main game activity for 2048
 */
public class GameActivity2048 extends AppCompatActivity implements Observer, AccountConstants {
    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Account information variables
     */
    private Account account;
    private ArrayList<Account> allAccounts = new ArrayList<>();

    /**
     * The gridview to display the board on
     */
    private GestureDetectGridView2048 gridView;

    /**
     * Variables for column width and column height
     */
    private static int columnWidth, columnHeight;

    /**
     * The board manager for the game
     */
    public BoardManager2048 boardManager = new BoardManager2048();

    /**
     * The method that updates the display
     */
    public void display() {
        //  if (boardManager.getBoardStatus()) {
        //boardManager.getBoardScore().increaseScore();
        updateTileButtons();

        // makeToastFinishedText();
        TextView scoreDisplay = findViewById(R.id.score2048);
        scoreDisplay.setText(String.format("Score: %s",
                String.valueOf(boardManager.getBoardScore().getScoreValue())));
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        //saveFinalScore();
        //account.increaseExperience(MOVE_EXP);
        //
        if(!boardManager.gameFinished() &&! boardManager.gameOver()) {
            saveToFile(MenuActivity2048.TEMP_SAVE_FILENAME);
            saveToFile("save_auto" + "2048" + AccountActivity.username + ".ser");
        } else if (boardManager.gameFinished()){
            account.updateHighScores("2048",boardManager.getBoardScore());
            writeAccountFile();
        }
        // }
    }


    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        GameBoard board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != board.getNumRows(); row++) {
            for (int col = 0; col != board.getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(((Tile2048) board.getTileAt(row, col)).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        GameBoard board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / board.getNumRows();
            int col = nextPos % board.getNumCols();
            b.setBackgroundResource(((Tile2048) board.getTileAt(row, col)).getBackground());
            nextPos++;
        }
    }

    /**
     * Display that the board is finished
     */
    private void makeToastFinishedText() {
        Toast.makeText(this, "You can't save a finished game!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadFromFile(MenuActivity2048.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_2048_main);
        readFiles();

        addSaveButtonListener();
        addLoadButtonListener();

        gridView = findViewById(R.id.grid2);
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

                        columnWidth = displayWidth / 4;
                        columnHeight = displayHeight / 4;
                        display();
                    }
                });
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.saver);
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
        Button loadButton = findViewById(R.id.loader);
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
        tmp.putExtra("currentGame","2048" );
        startActivity(tmp);
    }

    /**
     * Switch to the load activity.
     */
    private void switchToLoadActivity() {
        Intent tmp = new Intent(this, LoadActivity.class);
        tmp.putExtra("currentGame","2048" );
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

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
