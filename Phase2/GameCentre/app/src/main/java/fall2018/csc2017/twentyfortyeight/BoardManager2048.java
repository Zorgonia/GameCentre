package fall2018.csc2017.twentyfortyeight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.Interfaces.Manageable;
import fall2018.csc2017.Score;

public class BoardManager2048 implements Manageable, Serializable {

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
        for (int x = 0; x != 16; x++) {
            Tile2048 tmp = board.getTileAt(x / 4, x % 4);
            if (tmp.getId() == 0 || board.hasEqualAdjacentTile(x / 4, x % 4)) {
                return false;
            }
        }
        setActiveStatus(false);
        return true;
    }


    @Override
    public boolean isValidMove(int instruction) {
        return true;
    }

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
     * Checks whether the move is valid, that is if with that move,
     * pieces can be combined and/or pieces can be moved
     *
     * @param direction the direction of the move to check
     */
    public boolean isValidMover(String direction) {
        for (int x = 0; x != 16; x++) {
            Tile2048 tmp = board.getTileAt(x / 4, x % 4);
            if (tmp.getId() != 0 && (board.hasEqualTileInDirection(x / 4, x % 4, direction) || board.hasAdjacentTileOf(x / 4, x % 4, direction, 0))) {
                return true;
            }
        }
        return false;
    }

    // ignores position
    // TODO: remove touchMove from the Manageable interface
    /**
     * Processes a slide movement by adjusting and combining tiles in the game board
     * Precondition: gameOver() or gameFinished() needs to be false
     * @param direction the direction of the slide
     */
    void touchMove(String direction) {
        if (direction.equals("up") || direction.equals("down")) {
            for (int col = 0; col != 4; col++) {
                adjustTilesCol(col, direction);
                combineDoublesCol(col, direction);
                adjustTilesCol(col, direction);
            }
        } else if (direction.equals("left") || direction.equals("right")) {
            for (int row = 0; row != 4; row++) {
                adjustTilesRow(row, direction);
                combineDoublesRow(row, direction);
                adjustTilesRow(row, direction);
            }
        }
        board.placeRandomTile();
    }

    /**
     * Adjusts the tiles in the given column position according to the direction
     * Precondition: vertDirection must be "up" or "down"
     * @param col           the column index
     * @param vertDirection "up" or "down"
     */
    private void adjustTilesCol(int col, String vertDirection) {
        int emptyIndex = -1, id;
        boolean dirIsUp = vertDirection.equals("up");
        int row = (dirIsUp) ? 0 : board.getNumRows() - 1;
        boolean loopCondition = (dirIsUp) ? (row < board.getNumRows()) : (row > -1);
        while (loopCondition) {
            id = board.getTileAt(row, col).getId();
            if (id == 0) {
                if (emptyIndex != -1) {
                    // updates the loop condition and the iteration since loop will skip
                    row = (dirIsUp) ? row + 1 : row - 1;
                    loopCondition = (dirIsUp) ? (row < board.getNumRows()) : (row > -1);
                    continue;
                }
                emptyIndex = row;
            }
            if (emptyIndex != -1 && id != 0) {
                board.placeNewTileAt(id, emptyIndex, col);
                board.removeTileAt(row, col);
                emptyIndex = (dirIsUp) ? emptyIndex + 1 : emptyIndex - 1;
            }
            row = (dirIsUp) ? row + 1 : row - 1;
            loopCondition = (dirIsUp) ? (row < board.getNumRows()) : (row > -1);
        }
    }

    /**
     * Adjusts the tiles in the given row position according to the direction
     * Precondition: sideDirection must be "left" or "right"
     * @param row the row index
     */
    private void adjustTilesRow(int row, String sideDirection) {
        int emptyIndex = -1, id;
        boolean dirIsLeft = sideDirection.equals("left");
        int col = (dirIsLeft) ? 0 : board.getNumCols() - 1;
        boolean loopCondition = (dirIsLeft) ? (col < board.getNumRows()) : (col > -1);
        while (loopCondition) {
            id = board.getTileAt(row, col).getId();
            if (id == 0) {
                if (emptyIndex != -1) {
                    // updates the loop condition and the iteration since loop will skip
                    col = (dirIsLeft) ? col + 1 : col - 1;
                    loopCondition = (dirIsLeft) ? (col < board.getNumRows()) : (col > -1);
                    continue;
                }
                emptyIndex = col;
            }
            if (emptyIndex != -1 && id != 0) {
                board.placeNewTileAt(id, row, emptyIndex);
                board.removeTileAt(row, col);
                emptyIndex = (dirIsLeft) ? emptyIndex + 1 : emptyIndex - 1;
            }
            col = (dirIsLeft) ? col + 1 : col - 1;
            loopCondition = (dirIsLeft) ? (col < board.getNumRows()) : (col > -1);
        }
    }

    /**
     * Combines pairs of tiles if both are adjacent in the given column and has same id
     * Precondition: vertDirection must be "up" or "down"
     * @param col           the given column's index
     * @param vertDirection "up" or "down"
     */
    private void combineDoublesCol(int col, String vertDirection) {
        boolean dirIsUp = vertDirection.equals("up");
        int valueCur, valueNext;
        int row = (dirIsUp) ? 0 : board.getNumRows() - 1;
        boolean loopCondition = (dirIsUp) ? (row < board.getNumRows() - 1) : (row > 0);
        while (loopCondition) {
            valueCur = board.getTileAt(row, col).getId();
            valueNext = (dirIsUp) ? board.getTileAt(row + 1, col).getId()
                    : board.getTileAt(row - 1, col).getId();
            if (valueCur == valueNext) {
                board.placeNewTileAt(2 * valueCur, row, col);
                score.increaseScore();
                if (dirIsUp) {
                    board.removeTileAt(row + 1, col);
                } else { // if right
                    board.removeTileAt(row - 1, col);
                }
                row = (dirIsUp) ? row + 2 : row - 2;
            } else {
                row = (dirIsUp) ? row + 1 : row - 1;
            }
            loopCondition = (dirIsUp) ? (row < board.getNumRows() - 1) : (row > 0);
        }
    }

    /**
     * Combine pairs of tiles if both are adjacent in the given row and has same id
     * Precondition: sideDirection must be "left" or "right"
     * @param row           the given rows's index
     * @param sideDirection "left" or "right"
     */
    private void combineDoublesRow(int row, String sideDirection) {
        boolean dirIsLeft = sideDirection.equals("left");
        int valueCur, valueNext;
        int col = (dirIsLeft) ? 0 : board.getNumCols() - 1;
        boolean loopCondition = (dirIsLeft) ? (col < board.getNumCols() - 1) : (col > 0);
        while (loopCondition) {
            valueCur = board.getTileAt(row, col).getId();
            valueNext = (dirIsLeft) ? board.getTileAt(row, col + 1).getId()
                    : board.getTileAt(row, col - 1).getId();
            if (valueCur == valueNext) {
                board.placeNewTileAt(2 * valueCur, row, col);
                score.increaseScore();
                if (dirIsLeft) {
                    board.removeTileAt(row, col + 1);
                } else {
                    board.removeTileAt(row, col - 1);
                }
                col = (dirIsLeft) ? col + 2 : col - 2;
            } else {
                col = (dirIsLeft) ? col + 1 : col - 1;
            }
            loopCondition = (dirIsLeft) ? (col < board.getNumCols() - 1) : (col > 0);
        }
    }
}

    // TODO: Don't delete below for now
    //        if (vertDirection.equals("up")) {
//            int value0;
//            int row = 0;
//            while (row < 4 - 1) {
//                if (board.getTileAt(row, col).getId() == board.getTileAt(row + 1, col).getId()) {
//                    value0 = board.getTileAt(row, col).getId();
//                    board.placeNewTileAt(2 * value0, row, col);
//                    board.removeTileAt(row + 1, col);
//                    score.increaseScore(2*value0);
//                    row += 2;
//                } else {
//                    row += 1;
//                }
//            }
//        } else if (vertDirection.equals("down")) {
//            int row = 4 - 1;
//            int value1;
//            while (row > 0) {
//                value1 = board.getTileAt(row, col).getId();
//                if (board.getTileAt(row, col).getId() == board.getTileAt(row - 1, col).getId()) {
//                    board.placeNewTileAt(2 * value1, row, col);
//                    board.removeTileAt(row - 1, col);
//                    score.increaseScore(2*value1);
//                    row -= 2;
//                } else {
//                    row -= 1;
//                }
//            }
//        }
//    }

    //    /**
//     * Adjusts the tiles in the given column position according to the direction
//     *
//     * @param col           the column index
//     * @param vertDirection "up" or "down"
//     */
//    private void adjustTilesCol(int col, String vertDirection) {
//        int emptyIndex = -1;
//        int id;
//        if (vertDirection.equals("up")) {
//            for (int row = 0; row < 4; row++) {
//                id = board.getTileAt(row, col).getId();
//                if (id == 0) {
//                    if (emptyIndex != -1) {
//                        continue;
//                    }
//                    emptyIndex = row;
//                }
//                if (emptyIndex != -1 && id != 0) {
//                    board.placeNewTileAt(id, emptyIndex, col);
//                    board.removeTileAt(row, col);
//                    emptyIndex += 1;
//                }
//            }
//        } else if (vertDirection.equals("down")) {
//            for (int row = 3; row > -1; row--) {
//                id = board.getTileAt(row, col).getId();
//                if (id == 0) {
//                    if (emptyIndex != -1) {
//                        continue;
//                    }
//                    emptyIndex = row;
//                }
//                if (emptyIndex != -1 && id != 0) {
//                    board.placeNewTileAt(id, emptyIndex, col);
//                    board.removeTileAt(row, col);
//                    emptyIndex -= 1;
//                }
//            }
//        }
//    }
    //        int emptyIndex = -1;
//        int id;
//        if (sideDirection.equals("left")) {
//            for (int col = 0; col < 4; col++) {
//                id = board.getTileAt(row, col).getId();
//                if (id == 0) {
//                    if (emptyIndex != -1) {
//                        continue;
//                    }
//                    emptyIndex = col;
//                }
//                if (emptyIndex != -1 && id != 0) {
//                    board.placeNewTileAt(id, row, emptyIndex);
//                    board.removeTileAt(row, col);
//                    emptyIndex += 1;
//                }
//            }
//        } else if (sideDirection.equals("right")) {
//            for (int col = 3; col > -1; col--) {
//                id = board.getTileAt(row, col).getId();
//                if (id == 0) {
//                    if (emptyIndex != -1) {
//                        continue;
//                    }
//                    emptyIndex = col;
//                }
//                if (emptyIndex != -1 && id != 0) {
//                    board.placeNewTileAt(id, row, emptyIndex);
//                    board.removeTileAt(row, col);
//                    emptyIndex -= 1;
//                }
//            }
//        }
//    }