package fall2018.csc2017.checkers;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.Move;
import fall2018.csc2017.slidingtiles.R;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class CheckerBoardAndTileTest {

    CheckerBoardManager checkerBoardManager;
    CheckerBoard checkerBoard;

    /**
     * Makes a board with one red checker remaining in the corner;
     */
    private void makeBasicBoard() {
        List<CheckerTile> tiles = new ArrayList<>();
        for (int x = 0; x < 48; x++) {
            tiles.add(new CheckerTile(0));
        }
        tiles.add(new CheckerTile(2));
        checkerBoard = new CheckerBoard(tiles, 7);
        checkerBoardManager = new CheckerBoardManager(checkerBoard);
    }

    /**
     * Makes a board of all blank tiles
     */
    private void makeBlankBoard(){
        List<CheckerTile> tiles = new ArrayList<>();
        for (int x = 0; x < 64; x++) {
            tiles.add(new CheckerTile(0));
        }
        checkerBoard = new CheckerBoard(tiles, 8);
        checkerBoardManager = new CheckerBoardManager(checkerBoard);
    }

    /**
     * Makes a CheckerBoardManager who's next move with capture a piece and king the capturing
     * piece
     */
    private void prepareCaptureAndKinging(){
        checkerBoardManager = new CheckerBoardManager();
        checkerBoardManager.getBoard().swapTiles(new Move(0, 0, 3, 6));
        checkerBoardManager.getBoard().swapTiles(new Move(2, 2, 5, 1));
    }

    /**
     * Makes a CheckerBoardManager who's next move will be two consecutive moves
     */
    private void preparePrimedCaptures(){
        checkerBoardManager = new CheckerBoardManager();
        checkerBoardManager.getBoard().swapTiles(new Move(0, 0, 5, 3));
        checkerBoardManager.getBoard().swapTiles(new Move(0, 2, 3, 5));
        checkerBoardManager.getBoard().swapTiles(new Move(3, 0, 2, 6));
    }



    @Test
    public void TileTest(){
        CheckerTile tileTest = new CheckerTile(1, 1);
        assertEquals(1, tileTest.getId());
        tileTest.setBackground(2);
        assertEquals(2, tileTest.getBackground());
        tileTest.setId(20);
        assertEquals(20, tileTest.getId());
    }

    @Test
    public void testReturningTiles() {
        makeBasicBoard();
        checkerBoard.setSelectedTilePos(48);
        assertEquals(48, checkerBoard.getSelectedTilePos());
    }

    @Test
    public void testSwapTiles(){
        makeBasicBoard();
        checkerBoard.swapTiles(new Move(6, 6, 5, 5));
        assertEquals(R.drawable.tile_checkers_red, checkerBoard.getTileAt(5, 5).getBackground());
        assertEquals(R.drawable.tile_checkers_blank, checkerBoard.getTileAt(6, 6).getBackground());
    }

    @Test
    public void testDestoryRecreate(){
        makeBasicBoard();
        checkerBoard.destroyPiece(6, 6);
        assertEquals(R.drawable.tile_checkers_blank, checkerBoard.getTileAt(6, 6).getBackground());
        checkerBoard.addPiece(5, 5, 2);
        assertEquals(R.drawable.tile_checkers_red, checkerBoard.getTileAt(5, 5).getBackground());
    }

    @Test
    public void testHighlight(){
        makeBasicBoard();
        ArrayList<Integer> highlighters = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            highlighters.add(i);
        }
        checkerBoard.highlight(highlighters);
        for(int i = 0; i < 20; i++) {
            assertEquals(R.drawable.tile_checkers_highlight, checkerBoard.getTileAt(i / 7, i % 7).getBackground());
        }
        checkerBoard.turnOffHighlight();
        for(int i = 0; i < 20; i++) {
            assertEquals(R.drawable.tile_checkers_blank, checkerBoard.getTileAt(i / 7, i % 7).getBackground());
        }
    }

    @Test
    public void testRefreshBoard(){
        makeBasicBoard();
        checkerBoardManager.refreshBoard();
        for (int i = 0; i < checkerBoardManager.BOARD_SIZE; i+= 2){
            assertEquals(R.drawable.tile_checkers_red, checkerBoardManager.getBoard().
                    getTileAt(0, i).getBackground());
            assertEquals(R.drawable.tile_checkers_blank, checkerBoardManager.getBoard().
                    getTileAt(0, i+1).getBackground());
            assertEquals(R.drawable.tile_checkers_blank, checkerBoardManager.getBoard().
                    getTileAt(1, i).getBackground());
            assertEquals(R.drawable.tile_checkers_red, checkerBoardManager.getBoard().
                    getTileAt(1, i+1).getBackground());
            assertEquals(R.drawable.tile_checkers_red, checkerBoardManager.getBoard().
                    getTileAt(2, i).getBackground());
            assertEquals(R.drawable.tile_checkers_blank, checkerBoardManager.getBoard().
                    getTileAt(2, i+1).getBackground());
            assertEquals(R.drawable.tile_checkers_blank, checkerBoardManager.getBoard().
                    getTileAt(3, i).getBackground());
            assertEquals(R.drawable.tile_checkers_blank, checkerBoardManager.getBoard().
                    getTileAt(3, i+1).getBackground());
            assertEquals(R.drawable.tile_checkers_blank, checkerBoardManager.getBoard().
                    getTileAt(4, i).getBackground());
            assertEquals(R.drawable.tile_checkers_blank, checkerBoardManager.getBoard().
                    getTileAt(4, i+1).getBackground());
            assertEquals(R.drawable.tile_checkers_blank, checkerBoardManager.getBoard().
                    getTileAt(5, i).getBackground());
            assertEquals(R.drawable.tile_checkers_black, checkerBoardManager.getBoard().
                    getTileAt(5, i+1).getBackground());
            assertEquals(R.drawable.tile_checkers_black, checkerBoardManager.getBoard().
                    getTileAt(6, i).getBackground());
            assertEquals(R.drawable.tile_checkers_blank, checkerBoardManager.getBoard().
                    getTileAt(6, i+1).getBackground());
            assertEquals(R.drawable.tile_checkers_blank, checkerBoardManager.getBoard().
                    getTileAt(7, i).getBackground());
            assertEquals(R.drawable.tile_checkers_black, checkerBoardManager.getBoard().
                    getTileAt(7, i+1).getBackground());
        }
    }

    @Test
    public void testIsValidTap(){
        checkerBoardManager = new CheckerBoardManager();
        assertFalse(checkerBoardManager.isValidTap(6));
        assertFalse(checkerBoardManager.getAvailableCapture());

        assertTrue(checkerBoardManager.isValidTap(41));
        assertFalse(checkerBoardManager.getAvailableCapture());

        checkerBoardManager.getBoard().swapTiles(new Move(5, 1, 0, 0));
        assertFalse(checkerBoardManager.isValidTap(43));
        assertTrue(checkerBoardManager.getAvailableCapture());

        assertTrue(checkerBoardManager.isValidTap(50));
        assertTrue(checkerBoardManager.getAvailableCapture());

        checkerBoardManager.setMovePhase1(false);
        assertTrue(checkerBoardManager.isValidTap(34));

        checkerBoardManager.setPrimedCapture(true);
        assertFalse(checkerBoardManager.isValidTap(50));

        ArrayList<Integer> highlight = new ArrayList<>();
        highlight.add(50);
        checkerBoardManager.getBoard().highlight(highlight);
        assertTrue(checkerBoardManager.isValidTap(50));
        assertFalse(checkerBoardManager.isValidTap(34));

        checkerBoardManager.setBoardToInactive();
        assertFalse(checkerBoardManager.isValidTap(50));
    }

    @Test
    public void testMove(){
        checkerBoardManager = new CheckerBoardManager();
        checkerBoardManager.setAvailableCapture(false);
        checkerBoardManager.touchMove(41);
        assertFalse(checkerBoardManager.getMovePhase1());
        assertEquals(41, checkerBoardManager.getBoard().getSelectedTilePos());

        checkerBoardManager.touchMove(4);
        assertTrue(checkerBoardManager.getMovePhase1());
        assertEquals(-1, checkerBoardManager.getBoard().getSelectedTilePos());

        checkerBoardManager.touchMove(41);
        checkerBoardManager.touchMove(32);
        assertEquals(checkerBoardManager.getBoard().getTileAt(32/8, 32%8).getBackground(),
                R.drawable.tile_checkers_black);
        assertEquals(checkerBoardManager.getBoard().getTileAt(41/8, 41%8).getBackground(),
                R.drawable.tile_checkers_blank);
        assertTrue(checkerBoardManager.getUndoStack().remove().
                equals(new Move(32/8, 32%8, 41/8, 41%8)));
        assertFalse(checkerBoardManager.getUndoStack().removeKinged());
        assertTrue(checkerBoardManager.getMovePhase1());
        assertEquals(checkerBoardManager.getTurnColour(), 2);

    }

    @Test
    public void testKingMove(){
        checkerBoardManager = new CheckerBoardManager();
        checkerBoardManager.getBoard().addPiece(3, 3, 3);
        checkerBoardManager.setAvailableCapture(false);
        checkerBoardManager.touchMove(27);
        checkerBoardManager.touchMove(36);
        assertEquals(checkerBoardManager.getBoard().getTileAt(36/8, 36%8).getBackground(),
                R.drawable.tile_checkers_black_king);
        assertEquals(checkerBoardManager.getBoard().getTileAt(27/8, 27%8).getBackground(),
                R.drawable.tile_checkers_blank);
        assertTrue(checkerBoardManager.getUndoStack().remove().
                equals(new Move(36/8, 36%8, 27/8, 27%8)));
        assertFalse(checkerBoardManager.getUndoStack().removeKinged());
        assertTrue(checkerBoardManager.getMovePhase1());
        assertEquals(checkerBoardManager.getTurnColour(), 2);



    }

    @Test
    public void testCaptureAndKinging(){
        prepareCaptureAndKinging();
        checkerBoardManager.setAvailableCapture(true);
        checkerBoardManager.touchMove(18);
        checkerBoardManager.touchMove(0);
        assertEquals(R.drawable.tile_checkers_black_king,
                checkerBoardManager.getBoard().getTileAt(0,0).getBackground());
        assertEquals(R.drawable.tile_checkers_blank,
                checkerBoardManager.getBoard().getTileAt(1,1).getBackground());
        assertEquals(R.drawable.tile_checkers_blank,
                checkerBoardManager.getBoard().getTileAt(2,2).getBackground());
        assertTrue(checkerBoardManager.getUndoStack().remove().
                equals(new Move(0/8, 0%8, 18/8, 18%8)));
        assertTrue(checkerBoardManager.getUndoStack().removeKinged());
        int[] actualCapture = checkerBoardManager.getUndoStack().removeCapture();
        assertEquals(9, actualCapture[0]);
        assertEquals(2, actualCapture[1]);
        assertFalse(checkerBoardManager.getUndoStack().removeIsPrimedCapture());

    }

    @Test
    public void testPrimedCaptures(){
        preparePrimedCaptures();
        checkerBoardManager.setAvailableCapture(true);
        checkerBoardManager.touchMove(50);
        checkerBoardManager.touchMove(36);
        assertEquals(R.drawable.tile_checkers_black,
                checkerBoardManager.getBoard().getTileAt(4,4).getBackground());
        assertEquals(R.drawable.tile_checkers_blank,
                checkerBoardManager.getBoard().getTileAt(5,3).getBackground());
        assertEquals(R.drawable.tile_checkers_blank,
                checkerBoardManager.getBoard().getTileAt(6,2).getBackground());
        assertTrue(checkerBoardManager.getUndoStack().remove().
                equals(new Move(36/8, 36%8, 50/8, 50%8)));
        assertFalse(checkerBoardManager.getUndoStack().removeKinged());
        int[] actualCapture = checkerBoardManager.getUndoStack().removeCapture();
        assertEquals(43, actualCapture[0]);
        assertEquals(2, actualCapture[1]);
        assertTrue(checkerBoardManager.getUndoStack().removeIsPrimedCapture());
        assertFalse(checkerBoardManager.getMovePhase1());
        assertEquals(1, checkerBoardManager.getTurnColour());

        checkerBoardManager.touchMove(22);
        assertEquals(R.drawable.tile_checkers_black,
                checkerBoardManager.getBoard().getTileAt(2,6).getBackground());
        assertEquals(R.drawable.tile_checkers_blank,
                checkerBoardManager.getBoard().getTileAt(3,5).getBackground());
        assertEquals(R.drawable.tile_checkers_blank,
                checkerBoardManager.getBoard().getTileAt(4,4).getBackground());
        assertTrue(checkerBoardManager.getUndoStack().remove().
                equals(new Move(22/8, 22%8, 36/8, 36%8)));
        assertFalse(checkerBoardManager.getUndoStack().removeKinged());
        actualCapture = checkerBoardManager.getUndoStack().removeCapture();
        assertEquals(29, actualCapture[0]);
        assertEquals(2, actualCapture[1]);
        assertFalse(checkerBoardManager.getUndoStack().removeIsPrimedCapture());
        assertTrue(checkerBoardManager.getMovePhase1());
        assertEquals(2, checkerBoardManager.getTurnColour());
    }

    @Test
    public void testUndoMove(){
        checkerBoardManager = new CheckerBoardManager();
        checkerBoardManager.setAvailableCapture(false);
        checkerBoardManager.touchMove(41);
        checkerBoardManager.touchMove(32);
        checkerBoardManager.undo();
        assertEquals(checkerBoardManager.getBoard().getTileAt(41/8, 41%8).getBackground(),
                R.drawable.tile_checkers_black);
        assertEquals(checkerBoardManager.getBoard().getTileAt(32/8, 32%8).getBackground(),
                R.drawable.tile_checkers_blank);
        assertTrue(checkerBoardManager.getMovePhase1());
        assertEquals(checkerBoardManager.getTurnColour(), 1);
    }

    @Test
    public void testUndoCaptureAndKinging(){
        prepareCaptureAndKinging();
        checkerBoardManager.setAvailableCapture(true);
        checkerBoardManager.touchMove(18);
        checkerBoardManager.touchMove(0);
        checkerBoardManager.undo();
        assertEquals(R.drawable.tile_checkers_blank,
                checkerBoardManager.getBoard().getTileAt(0,0).getBackground());
        assertEquals(R.drawable.tile_checkers_red,
                checkerBoardManager.getBoard().getTileAt(1,1).getBackground());
        assertEquals(R.drawable.tile_checkers_black,
                checkerBoardManager.getBoard().getTileAt(2,2).getBackground());

    }

    @Test
    public void testUndoPrimedCaptures(){
        preparePrimedCaptures();
        checkerBoardManager.setAvailableCapture(true);
        checkerBoardManager.touchMove(50);
        checkerBoardManager.touchMove(36);
        checkerBoardManager.touchMove(22);
        checkerBoardManager.undo();
        assertEquals(R.drawable.tile_checkers_blank,
                checkerBoardManager.getBoard().getTileAt(2,6).getBackground());
        assertEquals(R.drawable.tile_checkers_red,
                checkerBoardManager.getBoard().getTileAt(3,5).getBackground());
        assertEquals(R.drawable.tile_checkers_blank,
                checkerBoardManager.getBoard().getTileAt(4,4).getBackground());
        assertEquals(R.drawable.tile_checkers_red,
                checkerBoardManager.getBoard().getTileAt(5,3).getBackground());
        assertEquals(R.drawable.tile_checkers_black,
                checkerBoardManager.getBoard().getTileAt(6,2).getBackground());
    }

    @Test
    public void testAllMoveHighlights(){
        makeBlankBoard();
        checkerBoard.addPiece(0, 1, 2);
        checkerBoard.addPiece(1, 4, 2);
        checkerBoard.addPiece(2, 3, 1);
        checkerBoard.addPiece(2, 5, 1);
        checkerBoard.addPiece(6, 1, 1);
        checkerBoard.addPiece(5, 4, 1);
        checkerBoard.addPiece(4, 3, 2);
        checkerBoard.addPiece(4, 5, 2);
        checkerBoardManager.setAvailableCapture(false);

        checkerBoardManager.touchMove(44);
        assertEquals(R.drawable.tile_checkers_highlight,
                checkerBoard.getTileAt(3, 2).getBackground());
        assertEquals(R.drawable.tile_checkers_highlight,
                checkerBoard.getTileAt(3, 6).getBackground());

        checkerBoardManager.touchMove(0);
        checkerBoardManager.touchMove(49);
        assertEquals(R.drawable.tile_checkers_highlight,
                checkerBoard.getTileAt(5, 0).getBackground());
        assertEquals(R.drawable.tile_checkers_highlight,
                checkerBoard.getTileAt(5, 2).getBackground());
        checkerBoardManager.touchMove(40);

        checkerBoardManager.touchMove(1);
        assertEquals(R.drawable.tile_checkers_highlight,
                checkerBoard.getTileAt(1, 0).getBackground());
        assertEquals(R.drawable.tile_checkers_highlight,
                checkerBoard.getTileAt(1, 2).getBackground());

        checkerBoardManager.touchMove(0);
        checkerBoardManager.touchMove(12);
        assertEquals(R.drawable.tile_checkers_highlight,
                checkerBoard.getTileAt(3, 2).getBackground());
        assertEquals(R.drawable.tile_checkers_highlight,
                checkerBoard.getTileAt(3, 6).getBackground());

    }

    @Test
    public void testEmptyUndo(){
        checkerBoardManager = new CheckerBoardManager();
        assertFalse(checkerBoardManager.getUndoStack().removeIsPrimedCapture());
        assertFalse(checkerBoardManager.getUndoStack().removeKinged());
        assertNull(checkerBoardManager.getUndoStack().removeCapture());
    }

    @Test
    public void testGameFinished(){
        makeBlankBoard();
        checkerBoard.addPiece(0, 0, 1);
        assertTrue(checkerBoardManager.gameFinished());
        makeBlankBoard();
        checkerBoard.addPiece(0, 0, 2);
        assertTrue(checkerBoardManager.gameFinished());
        checkerBoard.addPiece(0, 1, 1);
        assertFalse(checkerBoardManager.gameFinished());
    }

    @Test
    public void testSetBoardToInactive(){
        checkerBoardManager = new CheckerBoardManager();
        checkerBoardManager.setBoardToInactive();
        assertFalse(checkerBoardManager.getBoardStatus());
    }


}
