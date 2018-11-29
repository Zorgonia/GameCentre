package fall2018.csc2017.checkers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.Account;
import fall2018.csc2017.Interfaces.AccountConstants;
import fall2018.csc2017.slidingtiles.CustomAdapter;
import fall2018.csc2017.slidingtiles.GestureDetectGridView;
import fall2018.csc2017.slidingtiles.R;

public class CheckerGameActivity extends AppCompatActivity implements Observer, AccountConstants {

    private Account account;
    private ArrayList<Account> allAccounts = new ArrayList<>();


    private CheckerBoardManager checkerBoardManager;

    private ArrayList<Button> tileButtons;

    private GestureDetectGridView gridView;
    private int columnWidth, columnHeight;
    private static final int BOARD_SIZE = 8;

    private boolean drawDeclared = false;

    /**
     * Update the visuals of the game, as well as autosave.
     */
    public void display(){
        TextView turnDisplay = findViewById(R.id.TurnDisplay);
        if (checkerBoardManager.getBoardStatus()) {
            updateTileButtons();
            gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
            if (checkerBoardManager.getTurnColour() == 1){
                turnDisplay.setText(String.format("Black's Turn"));
            } else {
                turnDisplay.setText(String.format("Red's Turn"));
            }
            if (checkerBoardManager.gameFinished()) {
                checkerBoardManager.setBoardToInactive();
                if (checkerBoardManager.getTurnColour() == 1){
                    turnDisplay.setText(String.format("Red Wins!"));
                }
                else {
                    turnDisplay.setText(String.format("Black Wins!"));
                }
                account.getCheckersScore().increaseScore();
                writeAccountFile();
                deleteSaveFile(CheckerMenuActivity.CHECKER_SAVE_FILE);
            } else {
                saveToFile(CheckerMenuActivity.CHECKER_SAVE_FILE);
            }
        }
        else if (drawDeclared){
            turnDisplay.setText(String.format("Tie Game!"));
        }
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        loadTempFromFile();
        readFiles();
        createTileButtons(this);
        setContentView(R.layout.activity_checkers_main_);
        addUndoButtonListener();
        addDrawButtonListener();
//        CHECKER_SAVE_FILE = "checker_save_" + AccountActivity.username + ".ser";

        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(checkerBoardManager.getBoard().getNumCols());
        gridView.setBoardManager(checkerBoardManager);
        checkerBoardManager.getBoard().addObserver(this);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / BOARD_SIZE;
                        columnHeight = displayHeight / BOARD_SIZE;
                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        CheckerBoard board = checkerBoardManager.getBoard();
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
        CheckerBoard board = checkerBoardManager.getBoard();
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
                if (checkerBoardManager.getBoardStatus()) {
                    checkerBoardManager.undo();
                }
            }
        });
    }

    /**
     * Activates the Draw button.
     */
    private void addDrawButtonListener(){
        Button drawButton = findViewById(R.id.drawButton);
        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawDeclared = true;
                checkerBoardManager.setBoardToInactive();
                display();
            }
        });

    }

    /**
     * Load the board manager from the temporary save
     */
    private void loadTempFromFile() {
        try {
            InputStream inputStream = this.openFileInput(CheckerMenuActivity.TEMP_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                checkerBoardManager = (CheckerBoardManager) input.readObject();
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
            outputStream.writeObject(checkerBoardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Activates the Undo button.
     */
    public void deleteSaveFile(String fileName){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(null);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

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

    public void update(Observable o, Object arg) {
        display();
    }
}
