package fall2018.csc2017.checkers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.slidingtiles.CustomAdapter;
import fall2018.csc2017.slidingtiles.GestureDetectGridView;
import fall2018.csc2017.slidingtiles.R;

public class CheckerGameActivity extends AppCompatActivity implements Observer {

    private CheckerBoardManager checkerBoardManager;

    private ArrayList<Button> tileButtons;

    private GestureDetectGridView gridView;
    private int columnWidth, columnHeight;
    private static final int BOARD_SIZE = 8;

    public static final String CHECKER_SAVE_FILE = "checker_save.ser";
    public static final String SINGLE_ACC_FILE = "account_single.ser";
    public static final String ACCOUNT_FILENAME = "account_file.ser";


    public void display(){
        if (checkerBoardManager.getBoardStatus()) {
            updateTileButtons();
            gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        }
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        loadTempFromFile();
        createTileButtons(this);
        setContentView(R.layout.activity_checkers_main_);
        addUndoButtonListener();
        addSaveButtonListener();
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

    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(CHECKER_SAVE_FILE);
            }
        });
    }

    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        //saveToFile(CheckerMenuActivity.TEMP_SAVE_FILENAME);
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


    public void update(Observable o, Object arg){
        display();
    }
}
