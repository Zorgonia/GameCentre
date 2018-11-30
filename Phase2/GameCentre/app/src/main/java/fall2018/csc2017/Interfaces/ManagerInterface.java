package fall2018.csc2017.Interfaces;

import fall2018.csc2017.ParentClasses.GameBoard;
import fall2018.csc2017.Score;

/**
 * An interface that must be implemented by all board managers
 */
public interface ManagerInterface {
    /**
     * Return the current board.
     */
    GameBoard getBoard();

    /**
     * Refreshes the tile set of the board (for a new game)
     */
    void refreshBoard();

    /**
     * Get score of the board
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
     * Return whether the tap at position is valid
     *
     * @param position position tapped by the user
     * @return true if tap is valid
     */
    boolean isValidTap(int position);

    /**
     * Perform the correct move or function according to the position tapped by the user
     * @param position position tapped by the user
     */
    void touchMove(int position);


    /**
     * Return true if the board is able to be interacted with
     *
     * @return true if board is able to be interacted with
     */
    boolean getBoardStatus();
}
