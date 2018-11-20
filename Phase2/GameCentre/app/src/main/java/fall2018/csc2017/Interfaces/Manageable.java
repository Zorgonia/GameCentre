package fall2018.csc2017.Interfaces;

import fall2018.csc2017.abstractClasses.GameBoard;
import fall2018.csc2017.slidingtiles.Score;

public interface Manageable {
    /**
     * Return the current board.
     */
     GameBoard getBoard();

    /**
     * Refreshes the tile set of the board (for a new game)
     */
    void refreshBoard();

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