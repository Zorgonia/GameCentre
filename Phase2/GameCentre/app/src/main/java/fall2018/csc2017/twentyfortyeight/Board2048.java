package fall2018.csc2017.twentyfortyeight;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import fall2018.csc2017.slidingtiles.R;
import android.support.annotation.NonNull;

public class Board2048 extends Observable implements Serializable, Iterable<Tile2048> {

    static int NUM_ROWS = 4;
    static int NUM_COLS = 4;

    /**
     * 2D array representing the current locations of each 2048 tiles
     * Row major order
     */
    private Tile2048[][] tiles = new Tile2048[NUM_ROWS][NUM_COLS];

    /**
     * A new 2048 board arranged in row-major order
     * Precondition: length of tiles = NUM_ROWS*NUM_COLS;
     * @param tiles 2D array of 2048 tiles
     */
    Board2048(List<Tile2048> tiles){
        Iterator<Tile2048> iterator2048 = tiles.iterator();
        for (int row = 0; row != Board2048.NUM_ROWS; row++) {
            for (int col = 0; col != Board2048.NUM_COLS; col++) {
                this.tiles[row][col] = iterator2048.next();
            }
        }
    }

    /**
     * Returns the 2048 tile at the specified location
     * @param row   row in the board
     * @param col   col in the board
     * @return Tile2048 at the specified row and col in the board
     */
    public Tile2048 getTileAt(int row, int col){
        return this.tiles[row][col];
    }

    /**
     * changes the tile at row, col to move to row1,col1
     * for row, col place a blank tile in its place
     * @param row current row
     * @param col current column
     * @param row1 next row
     * @param col1 next column
     */
    // TODO: maybe just call removeTileAt and placeNewTileAt
    public void changeTilePosition(int row, int col, int row1, int col1){
        Tile2048 tile = new Tile2048(getTileAt(row,col).getValue(),
                getTileAt(row,col).getBackground());
        this.tiles[row1][col1] = tile;
        this.tiles[row][col] = new Tile2048(0, R.drawable.tile_blank);
    }

    /**
     * changes the tile at row,col to a blank tile
     * @param row given row
     * @param col given column
     */
    public void removeTileAt(int row, int col){
        this.tiles[row][col] = new Tile2048(0, R.drawable.tile_blank);
    }

    /**
     * placed the tile with background id in the specified tile
     * @param backgroundID backgroundID of the 2048 tile
     * @param row row
     * @param col column
     */
    public void placeNewTileAt(int backgroundID, int row, int col){
        this.tiles[row][col] = new Tile2048(backgroundID);
    }

    @NonNull
    @Override
    public Iterator<Tile2048> iterator() {
        return new BoardIterator2048();
    }

    /**
     * iterator class for this class
     */
    private class BoardIterator2048 implements Iterator<Tile2048> {
        /**
         * The number of the next tile to be iterated over
         */
        int nextTile = 0;

        @Override
        public boolean hasNext() {
            return nextTile < NUM_COLS*NUM_ROWS;
        }

        @Override
        public Tile2048 next() {
            Tile2048 toReturn = tiles[nextTile / NUM_ROWS][nextTile % NUM_COLS];
            nextTile++;
            return toReturn;
        }
    }
}