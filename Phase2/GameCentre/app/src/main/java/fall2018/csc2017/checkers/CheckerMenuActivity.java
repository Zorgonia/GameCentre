package fall2018.csc2017.checkers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.slidingtiles.PersonalScoreBoardActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SaveActivity;
import fall2018.csc2017.slidingtiles.ScoreBoardActivity;

public class CheckerMenuActivity extends AppCompatActivity {
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "checker_save_file_tmp.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers_menu);
        addStartButtonListener();
        addLoadButtonListener();
        addHighScoresButtonListener();
        addPersonalScoresButtonListener();

    }

    private void addStartButtonListener(){
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameActivity();
            }
        });

    }

    private void switchToGameActivity() {
        Intent tmp = new Intent(this, CheckerGameActivity.class);
        startActivity(tmp);
    }

    private void switchToPersonalScoreBoardActivity() {
        Intent tmp = new Intent(this, PersonalScoreBoardActivity.class);
        startActivity(tmp);
    }

    private void switchToScoreBoardActivity() {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        startActivity(tmp);
    }

    private void addLoadButtonListener(){
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    private void addHighScoresButtonListener(){
        Button highScoresButton = findViewById(R.id.HighScoresButton);
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreBoardActivity();
            }
        });

    }

    private void addPersonalScoresButtonListener(){
        Button personalScoresButton = findViewById(R.id.PersonalScoresButton);
        personalScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPersonalScoreBoardActivity();
            }
        });

    }
}
