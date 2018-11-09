package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * An user's account. Store information such as password and username
 * Also stores game related information such high scores and the user's
 * level and experience points
 */
class Account implements Serializable, Comparable<Account>{
    /**
     * The data for an Account
     */
    private String password, username;

    /**
     * Stores a limited number of final scores for the Account
     * dictated by HIGH_SCORES_AMOUNT
     */
    private ArrayList<Score> highScores = new ArrayList<>();
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
    Account(String user, String pass) {
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
    String getUsername() {
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
        Collections.sort(highScores);
        if (highScores.size() > 0) {
            return highScores.get(0);
        } else {
            return new Score(0);
        }
    }

    /**
     * getter for highScores array
     * @return highScores array
     */
    ArrayList<Score> getHighScores(){
        return highScores;
    }

    /**
     * Setter for password
     * @param newPass new password to be set.
     */
    void setPassword(String newPass){
        this.password = newPass;
    }

    /**
     * adds s to highScores if its high enough
     * and replaces another score if s is higher
     */
    void updateHighScores(Score s) {
        highScores.add(s);
        Collections.sort(highScores);
        // re sizes highScores according to HIGH_SCORES_AMOUNT
        if (highScores.size() > HIGH_SCORES_AMOUNT){
            for (int i = HIGH_SCORES_AMOUNT; i < highScores.size(); i++){
                highScores.remove(i);
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
