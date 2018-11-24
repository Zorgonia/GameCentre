package fall2018.csc2017.slidingtiles;

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

import fall2018.csc2017.GameSelectActivity;
import fall2018.csc2017.Score;

public class PersonalScoreBoardActivity extends AppCompatActivity {

    private static Account account;
    private StringBuilder topScores;
    public static boolean highToLow = false;

    private static final int SCORE_BOARD_SIZE = 5;

    public static final String SINGLE_ACC_FILE = "account_single.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_score_board);
        readAccountFile();

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
        ArrayList<Score> scores = account.getHighScores();
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
