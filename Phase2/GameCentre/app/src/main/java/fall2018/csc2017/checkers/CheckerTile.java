package fall2018.csc2017.checkers;

import java.io.Serializable;

import fall2018.csc2017.abstractClasses.AbstractTile;
import fall2018.csc2017.slidingtiles.R;

public class CheckerTile extends AbstractTile implements Comparable<AbstractTile>, Serializable {

    public CheckerTile(int background, int id){
        super(id, background);
    }

    public CheckerTile(int backgroundId){
        super(backgroundId);
        Integer[] tileIds = {R.drawable.tile_blank, R.drawable.tile_checkers_black,
                R.drawable.tile_checkers_red, R.drawable.tile_checkers_black_king,
                R.drawable.tile_checkers_red_king, R.drawable.tile_checkers_highlight}; //May not require king images since checkers never start as kings
        background = tileIds[backgroundId];
    }

    public void setBackground(int background){
        this.background = background;
    }

    public void setId(int id){
        this.id = id;
    }
}
