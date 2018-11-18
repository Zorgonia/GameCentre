package fall2018.csc2017.Interfaces;

import java.util.List;

import fall2018.csc2017.abstractClasses.AbstractBoard;
import fall2018.csc2017.slidingtiles.Board;
import fall2018.csc2017.slidingtiles.Score;
import fall2018.csc2017.slidingtiles.Tile;

public interface ManageableBoard {
    /**
     * Return the current board.
     */
     AbstractBoard getBoard();

    /**
     * Refreshes the tile set of the board (for a new game)
     */
    void refreshBoard();

    /**
     * Checks if sliding tiles board is solvable.
     *
     * Algorithm source: https://math.stackexchange.com/questions/293527/how-to-check-if-a-8-puzzle-is-solvable
     *
     * @param listT list of tiles for a new board
     * @return true iff inversion total is even
     */
     boolean checkValid(List<Tile> listT);

    /**
     * Get score of the board in terms of number of moves
     *
     * @return the Score of the board
     */
     Score getBoardScore();

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
     boolean gameFinished();

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
     boolean isValidTap(int position);

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     * Update the Score
     *
     * @param position the position
     */
     void touchMove(int position);

    /**
     * Undo a move made by the player. Do nothing if move cannot be undone.
     * Score increases for Undo. This is INTENTIONAL.
     */
//    void undo();

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


}