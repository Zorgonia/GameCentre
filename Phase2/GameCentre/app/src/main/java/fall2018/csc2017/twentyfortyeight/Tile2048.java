package fall2018.csc2017.twentyfortyeight;

import android.support.annotation.NonNull;
import java.io.Serializable;

import fall2018.csc2017.slidingtiles.R;

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

    /**
     * A tile2048 with value and background
     *
     * @param value      the value
     * @param background the background
     */
    Tile2048(int value, int background){
        this.value = value;
        this.background = background;
    }

    /**
     * A tile2048 with a background id; looks up and set the background
     *
     * @param backgroundID the background id
     */
    Tile2048(int backgroundID){
        value = (int) Math.pow(2,(double) (backgroundID + 1));
        Integer[] tileIds = {R.drawable.tile2048_2, R.drawable.tile2048_4, R.drawable.tile2048_8,
                R.drawable.tile2048_16, R.drawable.tile2048_32, R.drawable.tile2048_64,
                R.drawable.tile2048_128, R.drawable.tile2048_256, R.drawable.tile2048_512,
                R.drawable.tile2048_1024, R.drawable.tile2048_2048};
        background = tileIds[backgroundID];
    }

    @Override
    public int compareTo(@NonNull Tile2048 o) {
        return o.value - this.value;
    }
}