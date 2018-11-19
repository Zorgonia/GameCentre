package fall2018.csc2017.checkers;

import java.io.Serializable;
import java.util.List;

import fall2018.csc2017.Interfaces.Manageable;
import fall2018.csc2017.slidingtiles.Board;
import fall2018.csc2017.slidingtiles.Score;
import fall2018.csc2017.slidingtiles.Tile;


public class CheckerBoardManager implements Serializable, Manageable {

    /**
     * the Board being managed
     */
    private Board board;

    /**
     * Create a new CheckerBoardManager
     */
    public CheckerBoardManager(){
        refreshBoard();
    }

    /**
     * Reset the board to the starting position
     */
    public void refreshBoard(){

    }

    /**
     * Return true if a game is complete, meaning one side has no more pieces or there is a draw
     * @return true if game is complete, false otherwise
     */
    public boolean gameFinished(){
        return false;
    }

    /**
     * Return true if a user taps on one of their own pieces they can move, or a place that
     * piece can move to after tapping one of their own pieces
     * @param position the tile to check
     * @return true if tap is valid, false otherwise
     */
    public boolean isValidTap(int position){
        //note: we can use a false result from this function to "cancel" a tap on a piece if the user wishes to move a different piece
        return false;
    }

    /**
     * Return this CheckerBoardManager's Board
     * @return the board
     */
    public Board getBoard(){
        return null;
    }

    /**
     * Process a checker move by the player, swapping and replacing tiles as appropriate
     * @param position the position being moved to
     */
    public void touchMove(int position){

    }

    /**
     * Get score of the board in terms of number of moves of player 1
     * @return score of the board
     */
    public Score getBoardScore(){
        return null;
    }




}
