package fall2018.csc2017.checkers;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.Move;
import fall2018.csc2017.slidingtiles.R;

import static org.junit.Assert.assertEquals;

public class CheckerBoardAndTileTest {

    CheckerBoard checkerBoard;

    /**
     * Makes a board one red checker remaining in the corner;
     */
    private void makeInitial() {
        List<CheckerTile> tiles = new ArrayList<>();
        for (int x = 0; x < 48; x++) {
            tiles.add(new CheckerTile(0));
        }
        tiles.add(new CheckerTile(2));
        checkerBoard = new CheckerBoard(tiles, 7);
//        checkerBoardManager = new CheckerBoardManager(checkerBoard);
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
        makeInitial();
        checkerBoard.setSelectedTilePos(48);
        assertEquals(48, checkerBoard.getSelectedTilePos());
    }

    @Test
    public void testSwapTiles(){
        makeInitial();
        checkerBoard.swapTiles(new Move(6, 6, 5, 5));
        assertEquals(R.drawable.tile_checkers_red, checkerBoard.getTileAt(5, 5).getBackground());
        assertEquals(R.drawable.tile_checkers_blank, checkerBoard.getTileAt(6, 6).getBackground());
    }

    @Test
    public void testDestoryRecreate(){
        makeInitial();
        checkerBoard.destroyPiece(6, 6);
        assertEquals(R.drawable.tile_checkers_blank, checkerBoard.getTileAt(6, 6).getBackground());
        checkerBoard.addPiece(5, 5, 2);
        assertEquals(R.drawable.tile_checkers_red, checkerBoard.getTileAt(5, 5).getBackground());
    }

    @Test
    public void testHighlight(){
        makeInitial();
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


}
