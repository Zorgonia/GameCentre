package fall2018.csc2017.checkers;

import java.io.Serializable;

import fall2018.csc2017.abstractClasses.AbstractTile;
import fall2018.csc2017.slidingtiles.R;

public class CheckerTile extends AbstractTile implements Comparable<AbstractTile>, Serializable {

    public CheckerTile(int backgroundId, int id){
        super(id, backgroundId);
    }

    public CheckerTile(int backgroundId){
        super(backgroundId + 1);
        Integer[] tileIds = {R.drawable.tile_blank, R.drawable.tile_checkers_black,
                R.drawable.tile_checkers_red, R.drawable.tile_checkers_black_king,
                R.drawable.tile_checkers_red_king}; //May not require king images since checkers never start as kings
        background = tileIds[backgroundId];
    }
}
