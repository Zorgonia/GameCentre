package fall2018.csc2017.abstractClasses;

import android.support.annotation.NonNull;
import java.io.Serializable;

public abstract class AbstractTile implements Comparable<AbstractTile>, Serializable{
    protected int id, background;

    /**
     * A tile with id and background.
     *
     * @param id the corresponding ID of the tile
     * @param background the corresponding background ID
     */
    public AbstractTile(int id, int background) {
        this.id = id;
        this.background = background;
    }

    public AbstractTile(int id){
        this.id = id;
    }

    /**
     * Return id of a tile.
     * @return id of tile.
     */
    public int getId() {
        return id;
    }

    /**
     * Return background id of tile.
     * @return background id of tile.
     */
    public int getBackground() {
        return background;
    }

    @Override
    public int compareTo(@NonNull AbstractTile o) {
        return o.id - this.id;
    }
}
