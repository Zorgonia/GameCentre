package fall2018.csc2017.twentyfortyeight;

import java.io.Serializable;
import fall2018.csc2017.ParentClasses.ParentTile;
import fall2018.csc2017.slidingtiles.R;

/**
 * A 2048 game tile with id as the value of the tile
 * and background being the image reference of the tile
 */
public class Tile2048 extends ParentTile implements Comparable<ParentTile>, Serializable {
    /**
     * A tile with a id being the value of the tile
     * Precondition: id must be a power of 2 where (currently) id values ranges from 0 to 2048
     * @param id the value of the tile
     */
    Tile2048(int id) {
        super(id);
        // To add image references for higher id tiles add to the array in order, make sure
        // the last item in the array is always the blank tile image reference
        Integer[] tileIds = {R.drawable.tile2048_2, R.drawable.tile2048_4, R.drawable.tile2048_8,
                R.drawable.tile2048_16, R.drawable.tile2048_32, R.drawable.tile2048_64,
                R.drawable.tile2048_128, R.drawable.tile2048_256, R.drawable.tile2048_512,
                R.drawable.tile2048_1024, R.drawable.tile2048_2048, R.drawable.tile2048_blank};
        int backgroundID = id == 0
                ? (tileIds.length - 1) : (int) (Math.log((double) id)/Math.log((double) 2)) - 1;
        background = tileIds[backgroundID];
    }
}