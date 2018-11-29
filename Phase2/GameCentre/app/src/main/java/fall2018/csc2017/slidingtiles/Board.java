package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.Move;
import fall2018.csc2017.ParentClasses.GameBoard;

/**
 * The sliding tiles board.
 */
public class Board extends GameBoard<Tile> implements Serializable, Iterable<Tile> {

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles, int row, int col) {
        super(tiles, row, col);
    }

    /**
     * Swap the tiles as specified by move
     *
     * @param move the Move specifying which tiles to swap
     */
    void swapTiles(Move move) {
        Tile tempTile = new Tile(this.tiles[move.getRow1()][move.getCol1()].getId(),
                this.tiles[move.getRow1()][move.getCol1()].getBackground());

        this.tiles[move.getRow1()][move.getCol1()] = this.tiles[move.getRow2()][move.getCol2()];
        this.tiles[move.getRow2()][move.getCol2()] = tempTile;

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
}