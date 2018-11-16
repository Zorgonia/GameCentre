package fall2018.csc2017.twentyfortyeight;

import android.support.annotation.NonNull;
import java.io.Serializable;

import fall2018.csc2017.slidingtiles.R;

public class Tile2048 implements Comparable<Tile2048>, Serializable {

    private int background;
    private int value;

    public int getBackground() {
        return background;
    }

    public int getValue() {
        return value;
    }

    // Constructor
    Tile2048(int value, int background) {
        this.value = value;
        this.background = background;
    }

    Tile2048(int backgroundId) {
        value = backgroundId + 1;
        Integer[] tileIds = {R.drawable.tile_blank};
        background = tileIds[backgroundId];
    }

    @Override
    public int compareTo(@NonNull Tile2048 o) {
        return o.value - this.value;
    }
}
