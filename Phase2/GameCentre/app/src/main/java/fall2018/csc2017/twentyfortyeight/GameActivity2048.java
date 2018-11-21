package fall2018.csc2017.twentyfortyeight;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.abstractClasses.GameBoard;
import fall2018.csc2017.slidingtiles.CustomAdapter;
import fall2018.csc2017.slidingtiles.R;

/**
 * The main game activity for 2048
 */
public class GameActivity2048 extends AppCompatActivity implements Observer {
    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    private GestureDetectGridView2048 gridView;

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
        makeToastFinishedText();
            //TextView scoreDisplay = findViewById(R.id.ScoreDisplay);
            //scoreDisplay.setText(String.format("Score: %s", String.valueOf(boardManager.getBoardScore().getScoreValue())));
            gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
            //saveFinalScore();
            //account.increaseExperience(MOVE_EXP);
            //writeAccountFile();
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

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
