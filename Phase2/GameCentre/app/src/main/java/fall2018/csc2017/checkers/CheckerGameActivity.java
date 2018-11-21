package fall2018.csc2017.checkers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.slidingtiles.GestureDetectGridView;
import fall2018.csc2017.slidingtiles.R;

public class CheckerGameActivity extends AppCompatActivity implements Observer {

    private CheckerBoardManager checkerBoardManager;

    private ArrayList<Button> tileButtons;

    private GestureDetectGridView gridView;
    private int columnWidth, columnHeight;
    private static final int BOARD_SIZE = 8;

    public static final String SINGLE_ACC_FILE = "account_single.ser";
    public static final String ACCOUNT_FILENAME = "account_file.ser";


    public void display(){

    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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

    private void addSaveButtonListener() {
    }

    private void addUndoButtonListener() {
    }

    private void createTileButtons(Context context) {
    }

    public void update(Observable o, Object arg){

    }
}
