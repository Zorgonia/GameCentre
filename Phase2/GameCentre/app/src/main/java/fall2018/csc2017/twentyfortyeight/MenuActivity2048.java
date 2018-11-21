package fall2018.csc2017.twentyfortyeight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectOutputStream;

import fall2018.csc2017.abstractClasses.GameBoard;
import fall2018.csc2017.slidingtiles.ForgetActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.StartingActivity;

public class MenuActivity2048 extends AppCompatActivity {
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";

    private BoardManager2048 boardManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new BoardManager2048();
        setContentView(R.layout.activity_2048_menu);
        addButtonListener(R.id.newButton2048, GameActivity2048.class);
        addButtonListener(R.id.loadButton2048, ForgetActivity.class);
    }

    void addButtonListener(int id, Class c) {
        Button button = findViewById(id);
        final Class switchTo = c;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(switchTo);
            }
        });
    }

    private void switchToActivity(Class c) {
        Intent tmp = new Intent(this, c);
        tempSaveToFile();
        startActivity(tmp);
    }

    void listenAndSwitchNewGame() {
        Button button = findViewById(R.id.newButton2048);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                boardManager = new BoardManager2048();
                switchToActivity(GameActivity2048.class);
            }
        });
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
