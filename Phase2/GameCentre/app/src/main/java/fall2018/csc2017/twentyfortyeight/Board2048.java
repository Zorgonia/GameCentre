package fall2018.csc2017.twentyfortyeight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import fall2018.csc2017.ParentClasses.GameBoard;

/**
 * A board to hold tiles for the game 2048
 * Note that directions are all ints, with 1 = up, 2 = right, 3 = down, 4 = left
 */
class Board2048 extends GameBoard<Tile2048> implements Serializable, Iterable<Tile2048> {

    /**
     * A new 2048 game board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board2048(List<Tile2048> tiles, int row, int col) {
        super(tiles, row, col);
    }

    /**
     * changes the 2048 tile at row,col to a blank tile
     *
     * @param row given row
     * @param col given column
     */
    private void removeTileAt(int row, int col) {
        this.tiles[row][col] = new Tile2048(0);
    }

    /**
     * places a tile with id as the value at the specified row and column
     *
     * @param id  the value of the tile
     * @param row row
     * @param col column
     */
    private void placeNewTileAt(int id, int row, int col) {
        this.tiles[row][col] = new Tile2048(id);
    }

    /**
     * places a 2048 tile of id 2 at a random empty place in the board
     */
    void placeRandomTile() {
        ArrayList<Integer> emptyPositions = new ArrayList<>();
        int[] randomTileIds = {2, 2, 2, 2, 2, 2, 2, 2, 2, 4};
        //int[] randomTileIds = {512};
        Random rand = new Random();
        for (int x = 0; x < NUM_COLS * NUM_ROWS; x++) {
            if (getTileAt(x / NUM_ROWS, x % NUM_COLS).getId() == 0) {
                emptyPositions.add(x);
            }
        }
        int index = rand.nextInt(randomTileIds.length);
        if (emptyPositions.size() == 1) {
            placeNewTileAt(randomTileIds[index], emptyPositions.get(0) / NUM_ROWS,
                    emptyPositions.get(0) % NUM_COLS);
        } else if (emptyPositions.size() > 1) {
            int randIndex = rand.nextInt(emptyPositions.size() - 1);
            placeNewTileAt(randomTileIds[index], emptyPositions.get(randIndex) / NUM_ROWS,
                    emptyPositions.get(randIndex) % NUM_COLS);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Adjusts the tiles in the given column position according to the direction
     * Precondition: vertDirection must be 1 or 3 (1 is up, 3 is down)
     * @param col           the column index
     * @param vertDirection 1 or 3
     */
    private void adjustTilesCol(int col, int vertDirection) {
        int emptyIndex = -1, id;
        boolean dirIsUp = vertDirection == 1;
        int row = (dirIsUp) ? 0 : getNumRows() - 1;
        boolean loopCondition = (dirIsUp) ? (row < getNumRows()) : (row > -1);
        while (loopCondition) {
            id = getTileAt(row, col).getId();
            if (id == 0) {
                if (emptyIndex != -1) {
                    // updates the loop condition and the iteration since loop will skip
                    row = (dirIsUp) ? row + 1 : row - 1;
                    loopCondition = (dirIsUp) ? (row < getNumRows()) : (row > -1);
                    continue;
                }
                emptyIndex = row;
            }
            if (emptyIndex != -1 && id != 0) {
                placeNewTileAt(id, emptyIndex, col);
                removeTileAt(row, col);
                emptyIndex = (dirIsUp) ? emptyIndex + 1 : emptyIndex - 1;
            }
            row = (dirIsUp) ? row + 1 : row - 1;
            loopCondition = (dirIsUp) ? (row < getNumRows()) : (row > -1);
        }
    }

    /**
     * Adjusts the tiles in the given row position according to the direction
     * Precondition: sideDirection must be 4 or 2 (left = 4, right = 2)
     * @param row the row index
     * @param sideDirection 4 or 2
     */
    private void adjustTilesRow(int row, int sideDirection) {
        int emptyIndex = -1, id;
        boolean dirIsLeft = sideDirection == 4;
        int col = (dirIsLeft) ? 0 : getNumCols() - 1;
        boolean loopCondition = (dirIsLeft) ? (col < getNumRows()) : (col > -1);
        while (loopCondition) {
            id = getTileAt(row, col).getId();
            if (id == 0) {
                if (emptyIndex != -1) {
                    // updates the loop condition and the iteration since loop will skip
                    col = (dirIsLeft) ? col + 1 : col - 1;
                    loopCondition = (dirIsLeft) ? (col < getNumRows()) : (col > -1);
                    continue;
                }
                emptyIndex = col;
            }
            if (emptyIndex != -1 && id != 0) {
                placeNewTileAt(id, row, emptyIndex);
                removeTileAt(row, col);
                emptyIndex = (dirIsLeft) ? emptyIndex + 1 : emptyIndex - 1;
            }
            col = (dirIsLeft) ? col + 1 : col - 1;
            loopCondition = (dirIsLeft) ? (col < getNumRows()) : (col > -1);
        }
    }

    /**
     * Combines pairs of tiles if both are adjacent in the given column and has same id
     * Precondition: vertDirection must be 1 or 3 (up = 1, down = 3)
     * @param col           the given column's index
     * @param vertDirection 1 or 3
     */
    private void combineDoublesCol(int col, int vertDirection) {
        boolean dirIsUp = vertDirection == 1;
        int valueCur, valueNext;
        int row = (dirIsUp) ? 0 : getNumRows() - 1;
        boolean loopCondition = (dirIsUp) ? (row < getNumRows() - 1) : (row > 0);
        while (loopCondition) {
            valueCur = getTileAt(row, col).getId();
            valueNext = (dirIsUp) ? getTileAt(row + 1, col).getId()
                    : getTileAt(row - 1, col).getId();
            if (valueCur == valueNext) {
                placeNewTileAt(2 * valueCur, row, col);
                if (dirIsUp) {
                    removeTileAt(row + 1, col);
                } else { // if right
                    removeTileAt(row - 1, col);
                }
                row = (dirIsUp) ? row + 2 : row - 2;
            } else {
                row = (dirIsUp) ? row + 1 : row - 1;
            }
            loopCondition = (dirIsUp) ? (row < getNumRows() - 1) : (row > 0);
        }
    }

    /**
     * Combine pairs of tiles if both are adjacent in the given row and has same id
     * Precondition: sideDirection must be 4 or 2 (left = 4, right = 2)
     * @param row           the given rows's index
     * @param sideDirection "left" (4) or "right" (2)
     */
    private void combineDoublesRow(int row, int sideDirection) {
        boolean dirIsLeft = sideDirection == 4;
        int valueCur, valueNext;
        int col = (dirIsLeft) ? 0 : getNumCols() - 1;
        boolean loopCondition = (dirIsLeft) ? (col < getNumCols() - 1) : (col > 0);
        while (loopCondition) {
            valueCur = getTileAt(row, col).getId();
            valueNext = (dirIsLeft) ? getTileAt(row, col + 1).getId()
                    : getTileAt(row, col - 1).getId();
            if (valueCur == valueNext) {
                placeNewTileAt(2 * valueCur, row, col);
                if (dirIsLeft) {
                    removeTileAt(row, col + 1);
                } else {
                    removeTileAt(row, col - 1);
                }
                col = (dirIsLeft) ? col + 2 : col - 2;
            } else {
                col = (dirIsLeft) ? col + 1 : col - 1;
            }
            loopCondition = (dirIsLeft) ? (col < getNumCols() - 1) : (col > 0);
        }
    }

    /**
     * Processes a slide movement by adjusting and combining tiles in the game board
     * Precondition: gameOver() or gameFinished() needs to be false
     * @param direction the direction of the slide
     */
    void adjustBoardBy(int direction) {
        // up or down
        if (direction == 1 || direction == 3) {
            for (int col = 0; col < NUM_COLS; col++) {
                adjustTilesCol(col, direction);
                combineDoublesCol(col, direction);
                adjustTilesCol(col, direction);
            }
        } else if (direction == 4 || direction == 2) { // left or right
            for (int row = 0; row < NUM_ROWS; row++) {
                adjustTilesRow(row, direction);
                combineDoublesRow(row, direction);
                adjustTilesRow(row, direction);
            }
        }
    }

    /**
     * Returns a board2048 of the exact same arrangements as this board
     * @return board2048 with same tiles as this board
     */
    Board2048 getClone(){
        List<Tile2048> cloneTiles = new ArrayList<>();
        for (int row = 0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS; col++){
                cloneTiles.add(new Tile2048(getTileAt(row,col).getId()));
            }
        }
        return new Board2048(cloneTiles, NUM_ROWS, NUM_COLS);
    }
}