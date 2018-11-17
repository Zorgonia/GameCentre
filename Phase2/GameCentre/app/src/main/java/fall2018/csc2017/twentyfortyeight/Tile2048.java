package fall2018.csc2017.twentyfortyeight.;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class Tile2048 implements Comparable<Tile2048>, Serializable {

    /**
     * The background refers to image file
     */
    private int background;

    /**
     * the value of the image file
     */
    private int value;

    /**
     * returns the background value
     *
     * @return the background value
     */
    public int getBackground(){
        return background;
    }

    /**
     * returns the value
     *
     * @return the value
     */
    public int getValue(){
        return value;
    }


    Tile2048(int value, int background){
        this.value = value;
        this.background = background;
    }

    Tile2048(int backgroundID){
        Integer[] tileIds = {R.drawable.tile_1, R.drawable.tile_2, R.drawable.tile_3,
                R.drawable.tile_4, R.drawable.tile_5, R.drawable.tile_6, R.drawable.tile_7,
                R.drawable.tile_8, R.drawable.tile_9, R.drawable.tile_10, R.drawable.tile_11,
                R.drawable.tile_12, R.drawable.tile_13, R.drawable.tile_14, R.drawable.tile_15,
                R.drawable.tile_16, R.drawable.tile_17, R.drawable.tile_18, R.drawable.tile_19,
                R.drawable.tile_20, R.drawable.tile_21, R.drawable.tile_22, R.drawable.tile_23,
                R.drawable.tile_24, R.drawable.tile_blank};
    }

    @Override
    public int compareTo(@NonNull Tile2048 o) {
        return o.value - this.value;
    }
}