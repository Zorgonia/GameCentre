package fall2018.csc2017.slidingtiles;

import fall2018.csc2017.abstractClasses.AbstractTile;

import java.io.Serializable;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile extends AbstractTile implements Comparable<AbstractTile>, Serializable {


    Tile(int id, int background) {
        super(id, background);
    }
    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the background id
     */
     Tile(int backgroundId) {
        super(backgroundId + 1);
        //Array for tile Ids
        Integer[] tileIds = {R.drawable.tile_1, R.drawable.tile_2, R.drawable.tile_3,
                R.drawable.tile_4, R.drawable.tile_5, R.drawable.tile_6, R.drawable.tile_7,
                R.drawable.tile_8, R.drawable.tile_9, R.drawable.tile_10, R.drawable.tile_11,
                R.drawable.tile_12, R.drawable.tile_13, R.drawable.tile_14, R.drawable.tile_15,
                R.drawable.tile_16, R.drawable.tile_17, R.drawable.tile_18, R.drawable.tile_19,
                R.drawable.tile_20, R.drawable.tile_21, R.drawable.tile_22, R.drawable.tile_23,
                R.drawable.tile_24, R.drawable.tile_blank};
        background = tileIds[backgroundId];
    }
}
