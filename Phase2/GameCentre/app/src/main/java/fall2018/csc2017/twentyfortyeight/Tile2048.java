package fall2018.csc2017.twentyfortyeight;

import java.io.Serializable;
import fall2018.csc2017.abstractClasses.AbstractTile;
import fall2018.csc2017.slidingtiles.R;

/**
 * A 2048 game tile with id as the value of the tile
 * and background being the image reference of the tile
 */
public class Tile2048 extends AbstractTile implements Comparable<AbstractTile>, Serializable {

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    Tile2048(int id, int background) {
        super(id, background);
    }

    /**
     * A tile with a id being the value of the tile
     * Precondition: id must be a power of 2 where id values ranges from 0 to 2048
     * @param id the value of the tile
     */
    Tile2048(int id) {
        super(id);
        int backgroundID = id == 0 ? 11 : (int) (Math.log((double) id)/Math.log((double) 2)) - 1;
        //Array for 2048 tile Ids
        Integer[] tileIds = {R.drawable.tile2048_2, R.drawable.tile2048_4, R.drawable.tile2048_8,
                R.drawable.tile2048_16, R.drawable.tile2048_32, R.drawable.tile2048_64,
                R.drawable.tile2048_128, R.drawable.tile2048_256, R.drawable.tile2048_512,
                R.drawable.tile2048_1024, R.drawable.tile2048_2048, R.drawable.tile_blank};
        background = tileIds[backgroundID];
    }
}