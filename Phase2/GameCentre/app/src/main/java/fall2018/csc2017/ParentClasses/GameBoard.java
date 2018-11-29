package fall2018.csc2017.ParentClasses;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * Generic game board
 * @param <T> Class used to fill up the board
 */
public class GameBoard<T> extends Observable implements Serializable, Iterable<T> {

    /**
     * Number of rows in the board
     */
    protected int NUM_ROWS;

    /**
     * Number of columns in the board
     */
    protected int NUM_COLS;

    /**
     * stores instances of class T in 2d array
     * to represent the board
     */
    protected T[][] tiles;

    /**
     * Constructs a board filled of tiles of type T
     * @param tiles list in row major order filled with tiles of type T
     * @param numRow    number of rows
     * @param numCol    number of columns
     */
    @SuppressWarnings({"unchecked"})
    public GameBoard(List<T> tiles, int numRow, int numCol){
        Class<?> classT = tiles.get(0).getClass();
        Iterator<T> newIterator = tiles.iterator();
        NUM_ROWS = numRow;
        NUM_COLS = numCol;
        this.tiles = (T[][]) Array.newInstance(classT, numRow, numCol);
        for (int row = 0; row != NUM_ROWS; row++) {
            for (int col = 0; col != NUM_COLS; col++) {
                this.tiles[row][col] = newIterator.next();
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
     * returns the object of type T at the specified position in the board
     * @param row   the row position
     * @param col   the col position
     * @return  the T object at row,col in the board
     */
    public T getTileAt(int row, int col){
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

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Iterator class for iterating through objects of type T in the board
     */
    private class BoardIterator implements Iterator<T>{
        int nextTile = 0;

        @Override
        public boolean hasNext() {
            return nextTile < numTiles();
        }

        @Override
        public T next(){
            T toReturn = tiles[nextTile / NUM_ROWS][nextTile % NUM_COLS];
            nextTile++;
            return toReturn;
        }
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return new BoardIterator();
    }
}