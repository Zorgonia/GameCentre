package fall2018.csc2017.twentyfortyeight;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class Board2048Test {

    /**
     * Board Manager to be used in test
     */
    BoardManager2048 boardManager;

    /**
     * Makes a board with 2 in the bottom right and blanks everywhere else;
     */
    private void makeInitial() {
        List<Tile2048> tiles = new ArrayList<>();
        for (int x = 0; x < 15; x++) {
            tiles.add(new Tile2048(0));
        }
        tiles.add(new Tile2048(2));
        assignBoard(tiles);
    }

    private void makeAlmostSolved() {
        List<Tile2048> tiles = new ArrayList<>();
        for (int x = 0; x < 14; x++) {
            tiles.add(new Tile2048(0));
        }
        tiles.add(new Tile2048(1024));
        tiles.add(new Tile2048(1024));
        assignBoard(tiles);
    }

    private void makeAlmostLost() {
        List<Tile2048> tiles = new ArrayList<>();
        for (int x = 0; x < 2; x++) {
            tiles.add(new Tile2048(16));
            tiles.add(new Tile2048(8));
            tiles.add(new Tile2048(16));
            tiles.add(new Tile2048(32));
            tiles.add(new Tile2048(64));
            tiles.add(new Tile2048(128));
            tiles.add(new Tile2048(256));
            tiles.add(new Tile2048(512));
        }
        tiles.remove(15);
        tiles.add(new Tile2048(0));
        //tiles.add(new Tile2048(1024));
        //tiles.add(new Tile2048(1024));
        assignBoard(tiles);
    }

    /**
     * Make a specific board that can test all directions
     */
    private void makeMultipleCombines() {
        List<Tile2048> tiles = new ArrayList<>();
        tiles.add(new Tile2048(16));
        tiles.add(new Tile2048(8));
        tiles.add(new Tile2048(16));
        tiles.add(new Tile2048(8));
        tiles.add(new Tile2048(128));
        tiles.add(new Tile2048(256));
        tiles.add(new Tile2048(8));
        tiles.add(new Tile2048(256));
        tiles.add(new Tile2048(32));
        tiles.add(new Tile2048(64));
        tiles.add(new Tile2048(16));
        tiles.add(new Tile2048(4));
        tiles.add(new Tile2048(128));
        tiles.add(new Tile2048(8));
        tiles.add(new Tile2048(2));
        tiles.add(new Tile2048(2));
        assignBoard(tiles);
    }
    private void assignBoard(List<Tile2048> tiles) {
        Board2048 b = new Board2048(tiles, 4, 4);
        //b.placeRandomTile();
        boardManager = new BoardManager2048(b);
    }

    @Test
    public void isSolved() {
        makeAlmostSolved();
        assertFalse(boardManager.gameFinished());
        boardManager.touchMove(2);
        assertTrue(boardManager.gameFinished());
    }

    @Test
    public void randomTilePlaced() {
        makeInitial();
        Tile2048 tmp = boardManager.getBoard().getTileAt(3, 0);
        boardManager.touchMove(4);
        assertNotEquals(tmp, boardManager.getBoard().getTileAt(3, 0));
    }

    @Test
    public void testIsValidMove() {
        makeAlmostLost();
        assertEquals(boardManager.isValidTap(4),false);
        assertEquals(boardManager.isValidTap(1), false);
        assertEquals(boardManager.isValidTap(2),true );
    }

    @Test
    public void testLosing() {
        makeAlmostLost();
        assertEquals(boardManager.gameLost(), false);
        assertEquals(boardManager.getBoardStatus(), true);
        boardManager.touchMove(3);
        assertEquals(boardManager.gameFinished(), false);
        assertEquals(boardManager.gameLost(), true);
        assertEquals(boardManager.getBoardStatus(), false);
    }

    @Test
    public void refreshBoardTest(){
        makeAlmostLost();
        assertEquals(boardManager.getBoard().getTileAt(0, 0).getId()==16, true);
        boardManager = new BoardManager2048();
        Tile2048 tmp = boardManager.getBoard().getTileAt(0, 0);
        assertEquals(tmp.getId()==0 || tmp.getId() == 2 || tmp.getId() == 4, true);
    }

    @Test
    public void testScore() {
        makeInitial();
        assertEquals(boardManager.getBoardScore().getScoreValue() == 0, true);
        boardManager.touchMove(1);
        assertEquals(boardManager.getBoardScore().getScoreValue() == 1, true);
    }

    @Test
    public void testCombining() {
        makeMultipleCombines();
        assertEquals(boardManager.getBoard().getTileAt(3, 2).getId() == 2, true);
        assertEquals(boardManager.getBoard().getTileAt(3, 3).getId() == 2, true);
        boardManager.touchMove(2);
        assertEquals(boardManager.getBoard().getTileAt(3, 3).getId() == 4, true);
        assertEquals(boardManager.getBoard().getTileAt(2, 3).getId() == 4, true);
        boardManager.touchMove(3);
        assertEquals(boardManager.getBoard().getTileAt(3, 3).getId() == 8, true);
        assertEquals(boardManager.getBoard().getTileAt(3, 2).getId() == 8, true);
        boardManager.touchMove(4);
        assertEquals(boardManager.getBoard().getTileAt(3, 2).getId() == 16, true);
        assertEquals(boardManager.getBoard().getTileAt(2, 2).getId() == 16, true);
        assertEquals(boardManager.getBoard().getTileAt(0, 2).getId() == 16, true);
        assertEquals(boardManager.getBoard().getTileAt(1, 2).getId() == 16, true);
        boardManager.touchMove(1);
        assertEquals(boardManager.getBoard().getTileAt(0, 2).getId() == 32, true);
        assertEquals(boardManager.getBoard().getTileAt(1, 2).getId() == 32, true);
    }
}
