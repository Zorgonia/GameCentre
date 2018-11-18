package fall2018.csc2017.checkers;

import fall2018.csc2017.abstractClasses.AbstractBoard;
import fall2018.csc2017.slidingtiles.Tile;

import java.io.Serializable;
import java.util.List;

public class CheckerBoard extends AbstractBoard implements Serializable, Iterable<Tile>{

    public CheckerBoard(List<Tile> tiles, int numRow, int numCol){
        super(tiles, numRow, numCol);
    }

}
