package fall2018.csc2017.twentyfortyeight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.Interfaces.ManagerInterface;
import fall2018.csc2017.Score;

/**
 * A board manager for the 2048 board. Controls moves.
 */
public class BoardManager2048 implements Serializable, ManagerInterface {

    /**
     * The board being managed.
     */
    private Board2048 board;

    /**
     * false indicates game is finished and board isn't active any longer
     */
    private boolean activeStatus = true;

    /**
     * the current board's score
     */
    private Score score = new Score(0);

    /**
     * Return the current board.
     */
    public Board2048 getBoard() {
        return board;
    }


    /**
     * Manage a new shuffled board.
     */
    BoardManager2048() {
        refreshBoard();
    }

    /**
     * A constructor that takes in a board to be used instead
     */
    BoardManager2048(Board2048 B) {
        board = B;
        setActiveStatus(true);
    }

    /**
     * board is disabled for playing when false, playable otherwise
     *
     * @param activeStatus true => playable, false => disabled
     */
    private void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    /**
     * Changes the current board to a new 2048 game
     */
    public void refreshBoard() {
        // To implement complexity pass in row and column in the method header and change the next
        // 2 lines accordingly
        int numRows = 4;
        int numCols = 4;
        List<Tile2048> tiles = new ArrayList<>();
        for (int x = 0; x < numRows * numCols; x++) {
            tiles.add(new Tile2048(0));
        }
        this.board = new Board2048(tiles, numRows, numCols);
        this.board.placeRandomTile();
        setActiveStatus(true);
        score = new Score(0);
    }

    /**
     * Returns true if game is won : highest tile possible achieved
     *
     * @return true if game finishes, false otherwise
     */
    public boolean gameFinished() {
        for (int x = 0; x < 16; x++) {
            if (board.getTileAt(x / board.getNumRows(),
                    x % board.getNumRows()).getId() == 2048) {
                setActiveStatus(false);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if game is lost : no more moves can be made and 2048 isn't achieved
     *
     * @return true or false indicating loss
     */
    boolean gameLost() {
        for (int dir = 0; dir < 4; dir++) {
            if (isValidMove(dir)) {

                return false;
            }
        }
        setActiveStatus(false);
        return true;
    }

    @Override
    public boolean isValidMove(int instruction) {
        Board2048 tempClone = board.getClone();
        tempClone.adjustBoardBy(instruction);
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumCols(); col++) {
                if (tempClone.getTileAt(row, col).getId() != board.getTileAt(row, col).getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the Score of the current board
     *
     * @return the Score of the current board
     */
    public Score getBoardScore() {
        return score;
    }

    /**
     * returns true if board is playable, false if game is not playable (finished or over)
     *
     * @return board's active status
     */
    public boolean getBoardStatus() {
        return activeStatus;
    }

    /**
     * Processes a slide movement by adjusting and combining tiles in the game board
     * Precondition: gameLost() and gameFinished() needs to be false
     *
     * @param direction the direction of the slide
     */
    public void touchMove(int direction) {
        if (activeStatus) {
            board.adjustBoardBy(direction);
            score.increaseScore();
            board.placeRandomTile();
        }
    }
}