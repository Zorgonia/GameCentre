package fall2018.csc2017.checkers;

import java.io.Serializable;

import fall2018.csc2017.ParentClasses.ParentTile;
import fall2018.csc2017.slidingtiles.R;

public class CheckerTile extends ParentTile implements Comparable<ParentTile>, Serializable {
    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    public CheckerTile(int id, int background){
        super(id, background);
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the background id
     */
    public CheckerTile(int backgroundId){
        super(backgroundId);
        Integer[] tileIds = {R.drawable.tile_blank, R.drawable.tile_checkers_black,
                R.drawable.tile_checkers_red, R.drawable.tile_checkers_black_king,
                R.drawable.tile_checkers_red_king, R.drawable.tile_checkers_highlight}; //May not require king images since checkers never start as kings
        background = tileIds[backgroundId];
    }

    /**
     * If we need to set the tile to something new
     * @param background the background
     */
    public void setBackground(int background){
        this.background = background;
    }

    /**
     * If we want a new id
     * @param id the id
     */
    public void setId(int id){
        this.id = id;
    }
}
