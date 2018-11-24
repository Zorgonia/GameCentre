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

public class ScoreBoardActivity extends AppCompatActivity {
    private static final int SCORE_BOARD_SIZE = 10;

    private StringBuilder topScores;
    private StringBuilder topPlayers;

    /**
     * holds all made Accounts read from account_file.ser
     */
    public static String currentGame = "";
    public static boolean highToLow = false;
    private static ArrayList<Account> allAccounts;
    public static final String ACCOUNTS_FILENAME = "account_file.ser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromAccountsFile();
        setContentView(R.layout.activity_score_board);

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
            InputStream inputStream = this.openFileInput(ACCOUNTS_FILENAME);
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
            if (acc.getTopScore().getScoreValue() > 0) {
                alteredAllAccounts.add(acc);
            }
        }
        return alteredAllAccounts;
    }


    /**
     * updates topScores and topPlayers from allAccounts
     */
    // TODO: have scoreboard do it for specific games, not only sliding tiles
    private void fillDisplayValues(){
        ArrayList<Account> alteredAllAccounts = getScoreBoardAccounts();
        Collections.sort(alteredAllAccounts);
        if (highToLow){
            Collections.reverse(alteredAllAccounts);
        }
        int limit = Math.min(alteredAllAccounts.size(), SCORE_BOARD_SIZE);
        for (int i = 0; i < limit; i++) {
            String nextTopPlayer = alteredAllAccounts.get(i).getUsername()
                    + System.lineSeparator();
            topPlayers.append(nextTopPlayer);

            String nextTopScore = String.valueOf(alteredAllAccounts.get(i).getTopScore().getScoreValue())
                    + System.lineSeparator();
            topScores.append(nextTopScore);
        }
        for (int j = limit; j < SCORE_BOARD_SIZE; j++) {
            String nextTopPlayer = "  ----" + System.lineSeparator();
            topPlayers.append(nextTopPlayer);

            String nextTopScore ="  ----" + System.lineSeparator();
            topScores.append(nextTopScore);
        }
    }
}