package fall2018.csc2017.twentyfortyeight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.Interfaces.Manageable;
import fall2018.csc2017.slidingtiles.Score;

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
     * Changes the current board to a new 2048 game
     */
    // TODO: change method to allow for complexity
    public void refreshBoard() {
        List<Tile2048> tiles = new ArrayList<>();
        for (int x = 0; x != 16; x++) {
            tiles.add(new Tile2048(0));
        }
        this.board = new Board2048(tiles, 4, 4);
        this.board.placeRandomTile();
    }

    /**
     * Returns true if no moves can be made or a tile of 2048 is achieved
     * No moves can be made when no 2 tiles can be combined adjacently
     *
     * @return true if game finishes, false otherwise
     */
    public boolean gameFinished() {
        for (int row = 0; row != 4; row++) {
            for (int col = 0; col != 4; col++) {
                if (board.getTileAt(row, col).getId() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean gameOver() {
        for (int x = 0; x != 16; x++) {
            Tile2048 tmp = board.getTileAt(x / 4, x % 4);
            if (tmp.getId() == 0) {
                return false;
            }
            if (board.hasEqualAdjacentTile(x / 4, x % 4)) {
                return false;
            }
        }
        return true;
    }

    // TODO: implement score and getBoardScore
    public Score getBoardScore() {
        Score score = new Score(0);
        return score;
    }

    // TODO: return false when game is finished, true otherwise
    public boolean getBoardStatus() {
        return activeStatus;
    }

    // TODO: Not sure if we need this for 2048
    public boolean isValidMove(int direction) {
        return true;
    }


    // ignores position
    // TODO: remove touchMove from the Manageable interface
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

    ////////////////
    // TODO: remove duplicate code for the following methods
    ////////////////

    /**
     * Adjusts the tiles in the given column position according to the direction
     *
     * @param col           the column index
     * @param vertDirection "up" or "down"
     */
    private void adjustTilesCol(int col, String vertDirection) {
        int emptyIndex = -1;
        int id;
        if (vertDirection.equals("up")) {
            for (int row = 0; row < 4; row++) {
                id = board.getTileAt(row, col).getId();
                if (id == 0) {
                    if (emptyIndex != -1) {
                        continue;
                    }
                    emptyIndex = row;
                }
                if (emptyIndex != -1 && id != 0) {
                    board.placeNewTileAt(id, emptyIndex, col);
                    board.removeTileAt(row, col);
                    emptyIndex += 1;
                }
            }
        } else if (vertDirection.equals("down")) {
            for (int row = 3; row > -1; row--) {
                id = board.getTileAt(row, col).getId();
                if (id == 0) {
                    if (emptyIndex != -1) {
                        continue;
                    }
                    emptyIndex = row;
                }
                if (emptyIndex != -1 && id != 0) {
                    board.placeNewTileAt(id, emptyIndex, col);
                    board.removeTileAt(row, col);
                    emptyIndex -= 1;
                }
            }
        }
    }

    /**
     * Adjusts the tiles in the given row position according to the direction
     *
     * @param row the row index
     */
    private void adjustTilesRow(int row, String sideDirection) {
        int emptyIndex = -1;
        int id;
        if (sideDirection.equals("left")) {
            for (int col = 0; col < 4; col++) {
                id = board.getTileAt(row, col).getId();
                if (id == 0) {
                    if (emptyIndex != -1) {
                        continue;
                    }
                    emptyIndex = col;
                }
                if (emptyIndex != -1 && id != 0) {
                    board.placeNewTileAt(id, row, emptyIndex);
                    board.removeTileAt(row, col);
                    emptyIndex += 1;
                }
            }
        } else if (sideDirection.equals("right")) {
            for (int col = 3; col > -1; col--) {
                id = board.getTileAt(row, col).getId();
                if (id == 0) {
                    if (emptyIndex != -1) {
                        continue;
                    }
                    emptyIndex = col;
                }
                if (emptyIndex != -1 && id != 0) {
                    board.placeNewTileAt(id, row, emptyIndex);
                    board.removeTileAt(row, col);
                    emptyIndex -= 1;
                }
            }
        }
    }

    /**
     * Combines pairs of tiles if both are adjacent in the given column and has same id
     *
     * @param col           the given column's index
     * @param vertDirection "up" or "down"
     */
    // TODO: once game is functioning replace 4 with numRow
    private void combineDoublesCol(int col, String vertDirection) {
        if (vertDirection.equals("up")) {
            int value0;
            int row = 0;
            while (row < 4 - 1) {
                if (board.getTileAt(row, col).getId() == board.getTileAt(row + 1, col).getId()) {
                    value0 = board.getTileAt(row, col).getId();
                    board.placeNewTileAt(2 * value0, row, col);
                    board.removeTileAt(row + 1, col);
                    row += 2;
                } else {
                    row += 1;
                }
            }
        } else if (vertDirection.equals("down")) {
            int row = 4 - 1;
            int value1;
            while (row > 0) {
                value1 = board.getTileAt(row, col).getId();
                if (board.getTileAt(row, col).getId() == board.getTileAt(row - 1, col).getId()) {
                    board.placeNewTileAt(2 * value1, row, col);
                    board.removeTileAt(row - 1, col);
                    row -= 2;
                } else {
                    row -= 1;
                }
            }
        }
    }

    /**
     * Combine pairs of tiles if both are adjacent in the given row and has same id
     *
     * @param row           the given rows's index
     * @param sideDirection "left" or "right"
     */
    // TODO: once game is functioning replace 4 with numCol
    private void combineDoublesRow(int row, String sideDirection) {
        if (sideDirection.equals("left")) {
            int value0;
            int col = 0;
            while (col < 4 - 1) {
                value0 = board.getTileAt(row, col).getId();
                if (board.getTileAt(row, col).getId() == board.getTileAt(row, col + 1).getId()) {
                    board.placeNewTileAt(2 * value0, row, col);
                    board.removeTileAt(row, col + 1);
                    col += 2;
                } else {
                    col += 1;
                }
            }
        } else if (sideDirection.equals("right")) {
            int value1;
            int col = 4 - 1;
            while (col > 0) {
                value1 = board.getTileAt(row, col).getId();
                if (board.getTileAt(row, col).getId() == board.getTileAt(row, col - 1).getId()) {
                    board.placeNewTileAt(2 * value1, row, col);
                    board.removeTileAt(row, col - 1);
                    col -= 2;
                } else {
                    col -= 1;
                }
            }
        }
    }
}