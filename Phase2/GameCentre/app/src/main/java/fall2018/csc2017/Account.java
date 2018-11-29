package fall2018.csc2017;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import fall2018.csc2017.Interfaces.CurrentGameConstants;

/**
 * An user's account. Store information such as password and username
 * Also stores game related information such high scores and the user's
 * level and experience points
 */
public class Account implements Serializable, CurrentGameConstants {
    /**
     * The data for an Account
     */
    private String username;
    private HashMap<String, String> passwordMap = new HashMap<>();

    /**
     * Stores a limited number of final scores for the Account
     * dictated by HIGH_SCORES_AMOUNT
     */
    private ArrayList<Score> slidingTilesHighScores = new ArrayList<>();
    private ArrayList<Score> checkerHighScore = new ArrayList<>();
    private ArrayList<Score> highScores2048 = new ArrayList<>();
    private static final int HIGH_SCORES_AMOUNT = 5;

    /**
     * An account's level and experience points
     */
    private int experience;
    private int level;

    /**
     * the constant holds the amount of experience points per level
     */
    private static final int EXP_PER_LEVEL = 100;

    /**
     * Account with username and password.
     *
     * @param user the username of account
     * @param pass the password of account
     */
    public Account(String user, String pass) {
        username = user;
        passwordMap.put(user, pass);
        experience = 0;
        level = 0;
        checkerHighScore.add(new Score(0));
    }

    /**
     * Return username of Account.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Check if attempted password matched the password of the account.
     *
     * @return true if pass matches password, false otherwise
     */
    public boolean authenticate(String pass) {
        return passwordMap.get(getUsername()).equals(pass);
    }

    /**
     * returns top score (lowest number of moves)
     *
     * @return the Score that is top score (lowest number of moves) earned by the Account
     */
    public Score getTopScore(int currentGame){
        if (currentGame == SLIDING_TILES) {
            Collections.sort(slidingTilesHighScores);
            if (slidingTilesHighScores.size() > 0) {
                return slidingTilesHighScores.get(0);
            }
        } else if (currentGame == CHECKERS){
            return checkerHighScore.get(0);
        } else if (currentGame == TWENTYFORTYEIGHT){
            Collections.sort(highScores2048);
            if(highScores2048.size() > 0){
                return highScores2048.get(0);
            }
        }
        return new Score(0);
    }

    /**
     * getter for slidingTilesHighScores array
     * @return slidingTilesHighScores array
     */
    public ArrayList<Score> getHighScores(int currentGame){
        if (currentGame == SLIDING_TILES) {
            return slidingTilesHighScores;
        } else if (currentGame == CHECKERS){
            return checkerHighScore;
        } else if (currentGame == TWENTYFORTYEIGHT){
            return highScores2048;
        }
        return new ArrayList<>();
    }



    public Score getCheckersScore(){
        return checkerHighScore.get(0);
    }
    /**
     * Setter for password
     * @param newPass new password to be set.
     */
    public void setPassword(String newPass){
        passwordMap.replace(getUsername(), newPass);
    }

    /**
     * adds s to slidingTilesHighScores if its high enough
     * and replaces another score if s is higher
     */
    public void updateHighScores(String game, Score s) {
        ArrayList<Score> list;
        if (game.equals("Sliding Tiles")) {
                list = slidingTilesHighScores;
        } else if (game.equals("2048")) {
            list = highScores2048;
        } else {
            list = new ArrayList<>();
        }
        list.add(s);
        Collections.sort(list);
        // re sizes list according to HIGH_SCORES_AMOUNT
        if (list.size() > HIGH_SCORES_AMOUNT){
            for (int i = HIGH_SCORES_AMOUNT; i < list.size(); i++){
                list.remove(i);
            }
        }
    }

//    public

    /**
     * returns the user's level
     * @return  the user's level
     */
    public int getLevel(){
        return level;
    }

    /**
     * returns the user's experience points
     * @return  the user's experience
     */
    public int getExperience() {
        return experience;
    }

    /**
     * increased the account's experience
     * @param incr increment
     *
     */
    public void increaseExperience(int incr){
        experience += incr;
        level = (experience / EXP_PER_LEVEL) + 1;
    }
//
//    /**
//     * returns negative, 0, positive int if Account is lower,equal to or greater
//     * in score value than temp
//     * @param temp Account
//     * @return 1,0 or -1 depending on comparing value
//     */
//    public int compareTo(Account temp){
//        return Integer.compare(getTopScore().getScoreValue(), temp.getTopScore().getScoreValue());
//    }
}
