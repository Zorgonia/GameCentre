package fall2018.csc2017.checkers;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.twentyfortyeight.Tile2048;

import static org.junit.Assert.assertEquals;

public class CheckerBoardTest {

    CheckerBoardManager checkerBoardManager;
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
        checkerBoardManager = new CheckerBoardManager(checkerBoard);
    }

    @Test
    public void testReturningTiles() {
        checkerBoard.setSelectedTilePos(48);
        assertEquals(48, checkerBoard.getSelectedTilePos());
    }



}
