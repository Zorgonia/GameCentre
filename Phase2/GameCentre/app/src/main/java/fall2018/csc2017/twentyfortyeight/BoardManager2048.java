package fall2018.csc2017.twentyfortyeight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.Interfaces.TappableManager;
import fall2018.csc2017.Score;

public class BoardManager2048 implements Serializable, TappableManager {

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
    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    /**
     * Changes the current board to a new 2048 game
     */
    public void refreshBoard() {
        // To implement complexity pass in row and column in the method header
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

    // TODO: maybe combine gameFinished and gameOver

    /**
     * Returns true if game is won : highest tile possible achieved
     *
     * @return true if game finishes, false otherwise
     */
    public boolean gameFinished() {
        for (int x = 0; x < 16; x++) {
            if (board.getTileAt(x / 4, x % 4).getId() == 2048) {
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
    public boolean gameOver() {
        for (int dir = 0; dir < 4; dir++) {
            if (isValidTap(dir)) {
                return false;
            }
        }
        return true;
    }
//            tempClone.adjustBoardBy(dir);
//            for (int row = 0; row < board.getNumRows(); row++){
//                for (int col = 0; col < board.getNumCols(); col++){
//                    if (tempClone.getTileAt(row,col).getId() != tempClone.getTileAt(row,col).getId()){
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }


    @Override
    public boolean isValidTap(int instruction) {
        Board2048 tempClone = board.getClone();
        tempClone.adjustBoardBy(instruction);
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumCols(); col++) {
                if (tempClone.getTileAt(row, col).getId() != board.getTileAt(row, col).getId()) {
                    return true; // if a change is found in the clone then its a valid tap
                }
            }
        }
        return false;
    }

//        for (int dir = 0; dir < 4; dir++){
//            tempClone.adjustBoardBy(dir);
//            for (int row = 0; row < board.getNumRows(); row++){
//                for (int col = 0; col < board.getNumCols(); col++){
//                    if (tempClone.getTileAt(row,col).getId() != tempClone.getTileAt(row,col).getId()){
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }

    /**
     * Returns the Score of the current board
     *
     * @return the Score of the current board
     */
    public Score getBoardScore() {
        return score;
    }

    // TODO: return false when game is finished, true otherwise
    public boolean getBoardStatus() {
        return activeStatus;
    }

    /**
     * Processes a slide movement by adjusting and combining tiles in the game board
     * Precondition: gameOver() or gameFinished() needs to be false
     * @param direction the direction of the slide
     */
    public void touchMove(int direction) {
        board.adjustBoardBy(direction);
        score.increaseScore();
        board.placeRandomTile();
    }
}