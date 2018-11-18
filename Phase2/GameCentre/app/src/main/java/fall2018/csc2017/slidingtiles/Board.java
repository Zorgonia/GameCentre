package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.sql.SQLOutput;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fall2018.csc2017.abstractClasses.AbstractBoard;

/**
 * The sliding tiles board.
 */
public class Board extends AbstractBoard implements Serializable, Iterable<Tile> {

//    /**
//     * The number of rows.
//     */
//    static int NUM_ROWS = 3;
//
//    /**
//     * The number of rows.
//     */
//    static int NUM_COLS = 3;

//    /**
//     * The tiles on the board in row-major order.
//     */
//
//    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles, int row, int col) {
        super(tiles, row, col);
    }

//    /**
//     * Return the number of tiles on the board.
//     *
//     * @return the number of tiles on the board
//     */
//    int numTiles() {
//        return NUM_COLS * NUM_ROWS;
//    }
//
//    /**
//     * Return the tile at (row, col)
//     *
//     * @param row the tile row
//     * @param col the tile column
//     * @return the tile at (row, col)
//     */
//    Tile getTile(int row, int col) {
//        return tiles[row][col];
//    }

    /**
     * Swap the tiles as specified by move
     *
     * @param move the Move specifying which tiles to swap
     */
    void swapTiles(Move move) {
        // Need something to hold a Tile
        Tile tempTile = new Tile(this.tiles[move.getRow1()][move.getCol1()].getId() - 1);

        this.tiles[move.getRow1()][move.getCol1()] = this.tiles[move.getRow2()][move.getCol2()];
        this.tiles[move.getRow2()][move.getCol2()] = tempTile;

        setChanged();
        notifyObservers();
    }

//    /**
//     * Gets the number of columns in board
//     *
//     * @return number of columns in board
//     */
//    static int getNumCols() {
//        return NUM_COLS;
//    }


//    /**
//     * Get number of rows for board
//     *
//     * @return the number of rows in board
//     */
//    static int getNumRows() {
//        return NUM_ROWS;
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
