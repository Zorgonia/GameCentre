package fall2018.csc2017.twentyfortyeight;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import fall2018.csc2017.abstractClasses.GameBoard;

public class Board2048 extends GameBoard<Tile2048> implements Serializable, Iterable<Tile2048>{

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
     * @param row given row
     * @param col given column
     */
    void removeTileAt(int row, int col){
        this.tiles[row][col] = new Tile2048(0);
    }

    /**
     * places a tile with id as the value at the specified row and column
     * @param id the value of the tile
     * @param row row
     * @param col column
     */
    void placeNewTileAt(int id, int row, int col){
        this.tiles[row][col] = new Tile2048(id);
    }

    /**
     * places a 2048 tile of id 2 at a random empty place in the board
     */
    // TODO: implement this method more efficiently
    void placeRandomTile(){
        Random rand = new Random();
        int randRow = rand.nextInt(4);
        int randCol = rand.nextInt(4);
        mainLoop:
        for (int row = 0; row != 4; row++) {
            for (int col = 0; col != 4; col++){
                if (getTileAt(row,col).getId() == 0){
                    while(getTileAt(randRow,randCol).getId() != 0){
                        randCol = rand.nextInt(4);
                        randRow = rand.nextInt(4);
                    }
                    break mainLoop;
                }
            }
        }
        placeNewTileAt(2,randRow,randCol);
        setChanged();
        notifyObservers();
    }

    /**
     * returns true if any of the surrounding four tiles of the tile
     * at (row,col) has equal value. Return false otherwise
     * @param row row
     * @param col column
     */
    // TODO: this method is only needed in gameOver() in boardManager2048, might not be needed
    boolean hasEqualAdjacentTile(int row, int col){
        Tile2048 mainTile = getTileAt(row,col);
        // Are any of the 4 the blank tile?
        Tile2048 above = row == 0 ? null : getTileAt(row - 1, col);
        Tile2048 below = row == 3 ? null : getTileAt(row + 1, col);
        Tile2048 left = col == 0 ? null  : getTileAt(row, col - 1);
        Tile2048 right = col == 3 ? null : getTileAt(row, col + 1);
        return (below != null && below.getId() == mainTile.getId())
                || (above != null && above.getId() == mainTile.getId())
                || (left != null && left.getId() == mainTile.getId())
                || (right != null && right.getId() == mainTile.getId());
    }

    /**
     * Reverses the tiles position in given column
     * @param col the column index
     */
    void reverseCol(int col){
        for (int row = 0; row < NUM_ROWS/2; row++){
            Tile2048 temp = tiles[row][col];
            tiles[row][col] = tiles[NUM_ROWS - row - 1][col];
            tiles[NUM_ROWS - row - 1][col] = temp;
        }
    }

    /**
     * Reverses the tiles position in given row
     * @param row the column index
     */
    void reverseRow(int row){
        for (int col = 0; col < NUM_COLS/2; col++){
            Tile2048 temp = tiles[row][col];
            tiles[row][col] = tiles[row][NUM_COLS - col - 1];
            tiles[row][NUM_COLS - col - 1] = temp;
        }
    }
}