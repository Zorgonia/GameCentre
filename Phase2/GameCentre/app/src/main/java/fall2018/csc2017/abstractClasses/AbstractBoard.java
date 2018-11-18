package fall2018.csc2017.abstractClasses;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import fall2018.csc2017.slidingtiles.Move;
import fall2018.csc2017.slidingtiles.Tile;

/**
 * The sliding tiles board.
 */
public class AbstractBoard extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    protected int NUM_ROWS;

    /**
     * The number of rows.
     */
    protected int NUM_COLS;

    /**
     * The tiles on the board in row-major order.
     */

    protected Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    public AbstractBoard(List<Tile> tiles, int numRow, int numCol) {
        Iterator<Tile> iter = tiles.iterator();
        NUM_ROWS = numRow;
        NUM_COLS = numCol;
        this.tiles = new Tile[NUM_ROWS][NUM_COLS];
        for (int row = 0; row != NUM_ROWS; row++) {
            for (int col = 0; col != NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return NUM_COLS * NUM_ROWS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }


    /**
     * Gets the number of columns in board
     *
     * @return number of columns in board
     */
    public int getNumCols() {
        return NUM_COLS;
    }

    /**
     * Get number of rows for board
     *
     * @return the number of rows in board
     */
    public int getNumRows() {
        return NUM_ROWS;
    }
    //TODO: PENDING. SETNUMCOLS AND SETNUMROWS are not necessary as constructor sets them
//    /**
//     * Sets number of columns in board to numCols, if 3, 4, or 5
//     *
//     * @param numCols number of columns wanted to set to
//    //     */
//    public void setNumCols(int numCols) {
//        if (3 <= numCols && numCols <= 5) {
//            NUM_COLS = numCols;
//        }
//    }
//
//    /**
//     * Setter for number of rows in board, if numRows is 3, 4, or 5
//     *
//     * @param numRows number of rows wanted to set to
//     */
//    public void setNumRows(int numRows) {
//        if (3 <= numRows && numRows <= 5) {
//            NUM_ROWS = numRows;
//        }
//    }


    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();


    }

    /**
     * The Iterator for this class
     */
    private class BoardIterator implements Iterator<Tile> {
        /**
         * The number of the next tile to be iterated over
         */
        int nextTile = 0;

        @Override
        public boolean hasNext() {
            return nextTile < numTiles();
        }

        @Override
        public Tile next() {
            Tile toReturn = tiles[nextTile / NUM_ROWS][nextTile % NUM_COLS];
            nextTile++;
            return toReturn;
        }
    }
}
