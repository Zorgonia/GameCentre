package fall2018.csc2017.slidingtiles;

import java.io.Serializable;

/**
 * class Score to store different type of scoring of the game. I.e. time
 * For phase 1 score shall be tracked by the number of moves
 */
public class Score implements Serializable, Comparable<Score> {
    private int score;

    /**
     * A new score with a score of value
     */
    Score(int value){
        this.score = value;
    }

    /**
     * increases the score by 1
     */
    void increaseScore(){
        this.score += 1;
    }

    /**
     * decreases the score by 1
     */
    void decreaseScore(){
        this.score -= 1;
    }
    /**
     * return the int value of the score
     */
    int getScoreValue(){
        return this.score;
    }

    /**
     * compares Score
     * @param s: Score to compare
     * @return return -1,0,1 if this is lesser, equal or greater than s
     */
    public int compareTo(Score s){
        return Integer.compare(this.score, s.getScoreValue());
    }

    @Override
    public String toString(){
        return Integer.toString(this.score);
    }
}
