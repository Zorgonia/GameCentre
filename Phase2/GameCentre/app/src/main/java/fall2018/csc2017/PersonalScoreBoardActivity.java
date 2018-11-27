package fall2018.csc2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

import fall2018.csc2017.Interfaces.AccountConstants;
import fall2018.csc2017.slidingtiles.R;

public class PersonalScoreBoardActivity extends AppCompatActivity implements AccountConstants {

    private static Account account;
    private StringBuilder topScores;
    private boolean highToLow = false;
    private int currentGame = -1;

    private static final int SCORE_BOARD_SIZE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_score_board);
        readAccountFile();

        highToLow = getIntent().getBooleanExtra("highToLow", false);
        currentGame = getIntent().getIntExtra("currentGame", -1);

        topScores = new StringBuilder();
        fillTopScores();
        String displayText = account.getUsername() + ": " +
                "level: " + account.getLevel() + " " +
                "XP: " + account.getExperience();

        // displaying the values
        TextView title = findViewById(R.id.Title1);
        TextView score = findViewById(R.id.personalScores);
        title.setText(displayText);
        score.setText(topScores);

    }

    /**
     * reads from user's account file
     * and sets value for account
     */
    private void readAccountFile(){
        try {
            InputStream inputStream = this.openFileInput(SINGLE_ACC_FILE);
            if (inputStream != null){
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                PersonalScoreBoardActivity.account = (Account) objectInputStream.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("PersonalScores Activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("PersonalScores Activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("PersonalScores Activity", "File contained unexpected data type: "
                    + e.toString());
        }
    }

    /**
     * fills the topScores array from account file
     */
    private void fillTopScores() {
        ArrayList<Score> scores = account.getHighScores(currentGame);
        Collections.sort(scores);
        if (highToLow){
            Collections.reverse(scores);
        }
        int limit = Math.min(scores.size(), SCORE_BOARD_SIZE);
        for (int i = 0; i < limit; i++) {
            String nextTopScore = String.valueOf(scores.get(i).getScoreValue())
                    + System.lineSeparator();
            topScores.append(nextTopScore);
        }
        for (int j = limit; j < SCORE_BOARD_SIZE; j++) {
            String nextTopScore = " ---"
                    + System.lineSeparator();
            topScores.append(nextTopScore);
        }
    }
}
