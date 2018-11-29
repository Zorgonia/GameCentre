package fall2018.csc2017.twentyfortyeight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fall2018.csc2017.ParentClasses.GameBoard;

public class Board2048 extends GameBoard<Tile2048> implements Serializable, Iterable<Tile2048> {

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
    void removeTileAt(int row, int col) {
        this.tiles[row][col] = new Tile2048(0);
    }

    /**
     * places a tile with id as the value at the specified row and column
     *
     * @param id  the value of the tile
     * @param row row
     * @param col column
     */
    void placeNewTileAt(int id, int row, int col) {
        this.tiles[row][col] = new Tile2048(id);
    }

    /**
     * places a 2048 tile of id 2 at a random empty place in the board
     */
    void placeRandomTile() {
        ArrayList<Integer> emptyPositions = new ArrayList<>();
        int[] randomTileIds = {2, 2, 2, 2, 2, 2, 2, 2, 2, 4};
        //int[] randomTileIds = {128};
        Random rand = new Random();
        for (int x = 0; x < 16; x++) {
            if (getTileAt(x / 4, x % 4).getId() == 0) {
                emptyPositions.add(x);
            }
        }
        int index = rand.nextInt(randomTileIds.length);
        if (emptyPositions.size() == 1) {
            placeNewTileAt(randomTileIds[index], emptyPositions.get(0) / 4,
                    emptyPositions.get(0) % 4);
        } else if (emptyPositions.size() > 1) {
            int randIndex = rand.nextInt(emptyPositions.size() - 1);
            placeNewTileAt(randomTileIds[index], emptyPositions.get(randIndex) / 4,
                    emptyPositions.get(randIndex) % 4);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * returns true if any of the surrounding four tiles of the tile
     * at (row,col) has equal value. Return false otherwise
     *
     * @param row row
     * @param col column
     */
    // TODO: this method is only needed in gameOver() in boardManager2048, might not be needed
    boolean hasEqualAdjacentTile(int row, int col) {
//        Tile2048 mainTile = getTileAt(row, col);
//        // Are any of the 4 the blank tile?
//        Tile2048 above = row == 0 ? null : getTileAt(row - 1, col);
//        Tile2048 below = row == 3 ? null : getTileAt(row + 1, col);
//        Tile2048 left = col == 0 ? null : getTileAt(row, col - 1);
//        Tile2048 right = col == 3 ? null : getTileAt(row, col + 1);
//        return (below != null && below.getId() == mainTile.getId())
//                || (above != null && above.getId() == mainTile.getId())
//                || (left != null && left.getId() == mainTile.getId())
//                || (right != null && right.getId() == mainTile.getId());
        return (hasAdjacentTileOf(row, col, 1, getTileAt(row,col ).getId()) ||
                hasAdjacentTileOf(row, col, 3,getTileAt(row,col ).getId()) ||
                hasAdjacentTileOf(row, col, 4,getTileAt(row,col ).getId()) ||
                hasAdjacentTileOf(row, col, 2,getTileAt(row,col ).getId()));
    }

    /**
     * Checks if the tile at row, col has an adjacent tile with a specific id in the specified direction
     * @param row row of tile to check
     * @param col column of tile to check
     * @param direction the direction, 1 for up, 2 for right, 3 for down, 4 for left
     * @param id the id of the desired to check for
     * @return whether the tile in adjacent direction is equal
     */
    boolean hasAdjacentTileOf(int row, int col, int direction, int id) {
        if (direction == 1) {
            Tile2048 above = row == 0 ? null : getTileAt(row - 1, col);
            if (above == null) {
                return false;
            }
            return above.getId() == id;
        } else if (direction == 3) {
            Tile2048 below = row == 3 ? null : getTileAt(row + 1, col);
            if (below == null) {
                return false;
            }
            return below.getId() == id;
        } else if (direction == 4) {
            Tile2048 left = col == 0 ? null : getTileAt(row, col - 1);
            if (left == null) {
                return false;
            }
            return left.getId() == id;
        }
        Tile2048 right = col == 3 ? null : getTileAt(row, col + 1);
        if (right == null) {

            return false;
        }
        return right.getId() == id;
    }
}