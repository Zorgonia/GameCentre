package fall2018.csc2017.abstractClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.slidingtiles.Board;
import fall2018.csc2017.slidingtiles.Move;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.Score;
import fall2018.csc2017.slidingtiles.Tile;
import fall2018.csc2017.slidingtiles.UndoStack;

public abstract class AbstractBoardManager {

    /**
     * The board being managed.
     */
    protected Board board;

//    /**
//     * The stack of moves to be called when undo is requested
//     */
//    protected UndoStack undoStack;

//    /**
//     * Complexity of current board instance, 3 + complex is numRows=numCols
//     */
//    protected int complex = 0;

    /**
     * Score of the current board in terms of moves made
     */
    protected Score score = new Score(0);

    /**
     * false indicates game is solved and board isn't active any longer
     */
    protected boolean activeStatus = true;

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    protected AbstractBoardManager() {
        refreshBoard();
    }

    /**
     * Refreshes the tile set of the board (for a new game)
     */
    public abstract void refreshBoard();

    /**
     * Checks if sliding tiles board is solvable.
     *
     * Algorithm source: https://math.stackexchange.com/questions/293527/how-to-check-if-a-8-puzzle-is-solvable
     *
     * @param listT list of tiles for a new board
     * @return true iff inversion total is even
     */
    protected abstract boolean checkValid(List<Tile> listT);

    /**
     * Get score of the board in terms of number of moves
     *
     * @return the Score of the board
     */
    protected Score getBoardScore() {
        return score;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    protected abstract boolean gameFinished();

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    protected abstract boolean isValidTap(int position);

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     * Update the Score
     *
     * @param position the position
     */
    protected abstract void touchMove(int position);

    /**
     * Undo a move made by the player. Do nothing if move cannot be undone.
     * Score increases for Undo. This is INTENTIONAL.
     */
    abstract public void undo();

    /**
     * Add 1 to the size of this BoardManager's UndoStack if add is true, subtract 1 otherwise
     * @param increment: If true, add to size, otherwise subtract
     */
//    void incrementUndo(int increment){
//        undoStack.incrementSize(increment);
//    }

    /**
     * If limited is false, make undo have no move limit. If true, make it have a limit
     * @param limited: If false, make undo have no limit, if true make undo have a limit
     */
//    void setLimited(boolean limited){
//        undoStack.setLimited(limited);
//    }

    /**
     * Returns the complexity of the board manager
     *
     * @return the complexity of board manager
     */
//    int getComplex() {
//        return complex;
//    }

    /**
     * sets board activeStatus to false
     */
    protected void setBoardToInactive(){
        activeStatus = false;
    }

    /**
     * returns the board's active status
     * @return activeStatus: boolean
     */
    public boolean getBoardStatus(){
        return activeStatus;
    }

}
