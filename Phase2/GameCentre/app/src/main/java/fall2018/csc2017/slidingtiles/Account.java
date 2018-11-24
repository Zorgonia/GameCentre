package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import fall2018.csc2017.Score;

/**
 * An user's account. Store information such as password and username
 * Also stores game related information such high scores and the user's
 * level and experience points
 */
public class Account implements Serializable, Comparable<Account>{
    /**
     * The data for an Account
     */
    private String password, username;

    /**
     * Stores a limited number of final scores for the Account
     * dictated by HIGH_SCORES_AMOUNT
     */
    private ArrayList<Score> slidingTilesHighScores = new ArrayList<>();
    private Score checkerHighScore = new Score(0);
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
        password = pass;
        experience = 0;
        level = 0;
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
    boolean authenticate(String pass) {
        return password.equals(pass);
    }

    /**
     * returns top score (lowest number of moves)
     *
     * @return the Score that is top score (lowest number of moves) earned by the Account
     */
    Score getTopScore(){
        Collections.sort(slidingTilesHighScores);
        if (slidingTilesHighScores.size() > 0) {
            return slidingTilesHighScores.get(0);
        } else {
            return new Score(0);
        }
    }

    /**
     * getter for slidingTilesHighScores array
     * @return slidingTilesHighScores array
     */
    public ArrayList<Score> getSlidingTilesHighScores(){
        return slidingTilesHighScores;
    }
    public Score getCheckersScore(){
        return checkerHighScore;
    }
    /**
     * Setter for password
     * @param newPass new password to be set.
     */
    void setPassword(String newPass){
        this.password = newPass;
    }

    /**
     * adds s to slidingTilesHighScores if its high enough
     * and replaces another score if s is higher
     */
    void updateSlidingHighScores(Score s) {
        slidingTilesHighScores.add(s);
        Collections.sort(slidingTilesHighScores);
        // re sizes slidingTilesHighScores according to HIGH_SCORES_AMOUNT
        if (slidingTilesHighScores.size() > HIGH_SCORES_AMOUNT){
            for (int i = HIGH_SCORES_AMOUNT; i < slidingTilesHighScores.size(); i++){
                slidingTilesHighScores.remove(i);
            }
        }
    }


    /**
     * returns the user's level
     * @return  the user's level
     */
    int getLevel(){
        return level;
    }

    /**
     * returns the user's experience points
     * @return  the user's experience
     */
    int getExperience() {
        return experience;
    }

    /**
     * increased the account's experience
     * @param incr increment
     *
     */
    void increaseExperience(int incr){
        experience += incr;
        level = (experience / EXP_PER_LEVEL) + 1;
    }

    /**
     * returns negative, 0, positive int if Account is lower,equal to or greater
     * in score value than temp
     * @param temp Account
     * @return 1,0 or -1 depending on comparing value
     */
    public int compareTo(Account temp){
        return Integer.compare(getTopScore().getScoreValue(), temp.getTopScore().getScoreValue());
    }
}
