package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.Move;
import fall2018.csc2017.Score;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BoardAndTileTest {

    /** The board manager for testing. */
    private BoardManager boardManager;

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

    /**
     * Creates list of 9 Tiles with #8 and the blank swapped
     * @return list of Tiles
     */
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
     * Make a solved 4x4 Board.
     */
    private void setUpCorrect4() {
        List<Tile> tiles = makeTiles(4);
        Board board = new Board(tiles, 4, 4);
        boardManager = new BoardManager(board);
    }

    /**
     * Make a solved 5x5 Board.
     */
    private void setUpCorrect5() {
        List<Tile> tiles = makeTiles(5);
        Board board = new Board(tiles, 5, 5);
        boardManager = new BoardManager(board);
    }

    /**
     * Make a solved 3x3 Board.
     */
    private void setUpCorrect3() {
        List<Tile> tiles = makeTiles(3);
        Board board = new Board(tiles, 3, 3);
        boardManager = new BoardManager(board);
    }

    /**
     * Make a 3x3 board one move away from being solved.
     */
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
        setUpCorrect3();
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
        setUpCorrect4();
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
        assertTrue(boardManager.isValidMove(11));
        assertFalse(boardManager.isValidMove(15));
        assertFalse(boardManager.isValidMove(10));
    }

    /**
     * Make four moves, then undo four times.
     */
    private void moveAndUndo(){
        boardManager.touchMove(6);
        boardManager.touchMove(3);
        boardManager.touchMove(4);
        boardManager.touchMove(7);
        boardManager.undo();
        boardManager.undo();
        boardManager.undo();
        boardManager.undo();
    }

    /**
     * Attempt to undo a single move.
     */
    @Test
    public void testMoveAndUndo(){
        setUpAlmost();
        assertEquals(9, boardManager.getBoard().getTileAt(2, 1).getId());
        assertEquals(0, boardManager.getBoardScore().getScoreValue());
        boardManager.touchMove(6);
        assertEquals(9, boardManager.getBoard().getTileAt(2, 0).getId());
        boardManager.undo();
        assertEquals(9, boardManager.getBoard().getTileAt(2, 1).getId());
    }

    /**
     * Attempt to undo 4 times given limit of 3.
     */
    @Test
    public void testUndoLimit3(){
        setUpAlmost();
        moveAndUndo();
        assertEquals(9, boardManager.getBoard().getTileAt(2, 0).getId());
    }

    /**
     * Attempt to undo 4 times given limit of 4.
     */
    @Test
    public void testUndoLimit4(){
        setUpAlmost();
        boardManager.incrementUndo(1);
        moveAndUndo();
        assertEquals(9, boardManager.getBoard().getTileAt(2, 1).getId());
    }

    /**
     * Attempt to undo 4 times given limit of infinity.
     */
    @Test
    public void testUndoNoLimit(){
        setUpAlmost();
        boardManager.setLimited(false);
        moveAndUndo();
        assertEquals(9, boardManager.getBoard().getTileAt(2, 1).getId());
    }

    /**
     * Test whether the check for a valid list of tiles is correct.
     */
    @Test
    public void testCheckValid(){
        setUpAlmost();
        ArrayList<Tile> a1 = new ArrayList<>();
        a1.add(new Tile(0));
        a1.add(new Tile(1));
        a1.add(new Tile(2));
        assertTrue(boardManager.checkValid(a1));
        Tile t = a1.get(0);
        a1.set(0, a1.get(1));
        a1.set(1, t);
        assertFalse(boardManager.checkValid(a1));
    }

    /**
     * Test whether complexity is given correctly.
     */
    @Test
    public void testComplex(){
        boardManager = new BoardManager(2);
        assertEquals(2, boardManager.getComplex());
        boardManager = new BoardManager(1);
        assertEquals(1, boardManager.getComplex());
        boardManager = new BoardManager(0);
        assertEquals(0, boardManager.getComplex());
    }

    /**
     * Make a board inactive, test if it updates accordingly.
     */
    @Test
    public void testInactiveBoard(){
        setUpCorrect5();
        assertTrue(boardManager.getBoardStatus());
        boardManager.setBoardToInactive();
        assertFalse(boardManager.getBoardStatus());
    }

    /**
     * Test increment of Score.
     */
    @Test
    public void testScoreIncr(){
        Score s = new Score(0);
        s.increaseScore();
        s.increaseScore();
        assertEquals(2, s.getScoreValue());
        s.decreaseScore();
        s.decreaseScore();
        s.decreaseScore();
        assertEquals(-1, s.getScoreValue());
    }

    /**
     * Test Score comparator.
     */
    @Test
    public void testScoreComp(){
        Score s1 = new Score(5);
        Score s2 = new Score(5);
        assertEquals(0, s1.compareTo(s2));
        s1.increaseScore();
        assertEquals(1, s1.compareTo(s2));
    }

    //Covers BoardManager, UndoStack, Tile, Move, Score, and Board.

    //AccountTest covers Account.

    //Remaining Tests: GameActivity, SlidingTilesMenuActivity,
    //AccountActivity, ForgetActivity, LoadActivity, ScoreBoardActivity,
    //SaveActivity, PersonalScoreBoardActivity, SignUpActivity.
}

