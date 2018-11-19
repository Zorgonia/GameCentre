package fall2018.csc2017.checkers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.slidingtiles.R;

public class CheckerMenuActivity extends AppCompatActivity {
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
            }
        });

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
            }
        });

    }

    private void addPersonalScoresButtonListener(){
        Button personalScoresButton = findViewById(R.id.PersonalScoresButton);
        personalScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }
}
