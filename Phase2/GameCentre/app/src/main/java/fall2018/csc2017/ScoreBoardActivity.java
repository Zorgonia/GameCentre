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
import java.util.Comparator;

import fall2018.csc2017.Interfaces.AccountConstants;
import fall2018.csc2017.slidingtiles.R;

/**
 * An activity for the all time leaderboard (one score per user)
 */
public class ScoreBoardActivity extends AppCompatActivity implements AccountConstants {
    /**
     * Number of scores to display (ie number of unique users)
     */
    private static final int SCORE_BOARD_SIZE = 10;

    /**
     * StringBuilders for the top players and scores
     */
    private StringBuilder topScores;
    private StringBuilder topPlayers;

    /**
     * holds all made Accounts read from account_file.ser
     */
    private boolean highToLow = false;
    private int currentGame = -1;
    private static ArrayList<Account> allAccounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromAccountsFile();
        setContentView(R.layout.activity_score_board);
        highToLow = getIntent().getBooleanExtra("highToLow", false);
        currentGame = getIntent().getIntExtra("currentGame", -1);

        topScores = new StringBuilder();
        topPlayers = new StringBuilder();
        fillDisplayValues();

        // displaying the values
        TextView players = findViewById(R.id.topPlayers);
        TextView scores = findViewById(R.id.topScores);
        players.setText(topPlayers);
        scores.setText(topScores);
    }

    /**
     * reads from file containing all accounts
     * and updates the allAccounts arrayList accordingly
     */
    private void readFromAccountsFile() {
        try {
            InputStream inputStream = this.openFileInput(ACCOUNT_FILENAME);
            if (inputStream != null) {
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                ScoreBoardActivity.allAccounts = (ArrayList<Account>) objectInputStream.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("ScoreBoard Activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("ScoreBoard Activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("ScoreBoard Activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * returns arraylist<Account> that only contains accounts that are top 10 in scores
     * and have won atleast one game (top score isn't 0)
     *
     * @return arrayList<Account>
     */
    public ArrayList<Account> getScoreBoardAccounts(){
        ArrayList<Account> alteredAllAccounts = new ArrayList<>();
        for (Account acc : allAccounts) {
            if (acc.getTopScore(currentGame).getScoreValue() > 0) {
                alteredAllAccounts.add(acc);
            }
        }
        Collections.sort(alteredAllAccounts, new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                int comparison = Integer.compare(o1.getTopScore(currentGame).getScoreValue(),
                        o2.getTopScore(currentGame).getScoreValue());
                if (highToLow){
                    return (-1)*comparison;
                }
                return comparison;
            }
        });
        return alteredAllAccounts;
    }


    /**
     * updates topScores and topPlayers from allAccounts
     */
    private void fillDisplayValues(){
        ArrayList<Account> alteredAllAccounts = getScoreBoardAccounts();
        int limit = Math.min(alteredAllAccounts.size(), SCORE_BOARD_SIZE);
        for (int i = 0; i < limit; i++) {
            String nextTopPlayer = alteredAllAccounts.get(i).getUsername()
                    + System.lineSeparator();
            topPlayers.append(nextTopPlayer);

            String nextTopScore = String.valueOf(alteredAllAccounts.get(i).getTopScore(currentGame).getScoreValue())
                    + System.lineSeparator();
            topScores.append(nextTopScore);
        }
        for (int j = limit; j < SCORE_BOARD_SIZE; j++) {
            String nextTopPlayer = "----" + System.lineSeparator();
            topPlayers.append(nextTopPlayer);

            String nextTopScore ="----" + System.lineSeparator();
            topScores.append(nextTopScore);
        }
    }
}