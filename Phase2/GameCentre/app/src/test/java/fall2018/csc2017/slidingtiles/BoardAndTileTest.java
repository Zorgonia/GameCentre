package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BoardAndTileTest {

    /** The board manager for testing. */
    BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int complex) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complex*complex;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }
    private List<Tile> makeAlmost() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 7; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        tiles.add(new Tile(9, 8));
        tiles.add(new Tile(8, 7));
        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect4() {
        List<Tile> tiles = makeTiles(4);
        Board board = new Board(tiles, 4, 4);
        boardManager = new BoardManager(board);
    }

    private void setUpCorrect5() {
        List<Tile> tiles = makeTiles(5);
        Board board = new Board(tiles, 5, 5);
        boardManager = new BoardManager(board);
    }

    private void setUpCorrect3() {
        List<Tile> tiles = makeTiles(3);
        Board board = new Board(tiles, 3, 3);
        boardManager = new BoardManager(board);
    }

    private void setUpAlmost(){
        List<Tile> tiles = makeAlmost();
        Board board = new Board(tiles, 3, 3);
        boardManager = new BoardManager(board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(new Move(0, 0, 0, 1));
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect4();
        assertTrue(boardManager.gameFinished());
        swapFirstTwoTiles();
        assertFalse(boardManager.gameFinished());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect5();
        assertEquals(1, boardManager.getBoard().getTileAt(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTileAt(0, 1).getId());
        boardManager.getBoard().swapTiles(new Move(0, 0, 0, 1));
        assertEquals(2, boardManager.getBoard().getTileAt(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTileAt(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect3();
        assertEquals(15, boardManager.getBoard().getTileAt(3, 2).getId());
        assertEquals(16, boardManager.getBoard().getTileAt(3, 3).getId());
        boardManager.getBoard().swapTiles(new Move(3, 3, 3, 2));
        assertEquals(16, boardManager.getBoard().getTileAt(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTileAt(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect4();
        assertTrue(boardManager.isValidTap(11));
        assertFalse(boardManager.isValidTap(15));
        assertFalse(boardManager.isValidTap(10));
    }
}

