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
abstract public class AbstractBoard extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    protected static int NUM_ROWS;

    /**
     * The number of rows.
     */
    protected static int NUM_COLS;

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
    public AbstractBoard(List<Tile> tiles, int numRows, int numCols) {
        Iterator<Tile> iter = tiles.iterator();
        NUM_ROWS = numRows;
        NUM_COLS = numCols;
        this.tiles = new Tile[NUM_ROWS][NUM_COLS];
        for (int row = 0; row != AbstractBoard.NUM_ROWS; row++) {
            for (int col = 0; col != AbstractBoard.NUM_COLS; col++) {
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
    public static int getNumCols() {
        return NUM_COLS;
    }

    /**
     * Get number of rows for board
     *
     * @return the number of rows in board
     */
    public static int getNumRows() {
        return NUM_ROWS;
    }


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
