package fall2018.csc2017.Interfaces;

import fall2018.csc2017.abstractClasses.GameBoard;
import fall2018.csc2017.Score;

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
     * Return whether the player is able to make the requested move
     *
     * @param instruction data specifying the move
     * @return true if the mode is able to be made
     */
    boolean isValidMove(int instruction);
    //TODO: this parameter doesn't necessarily have to be an integer


    /**
     * Return true if the board is able to be interacted with
     *
     * @return true if board is able to be interacted with
     */
    boolean getBoardStatus();
}