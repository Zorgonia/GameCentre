package fall2018.csc2017;

import java.io.Serializable;

public class Move implements Serializable {
    /**
     * The row of the first tile to swap
     */
    private int row1;
    /**
     * The column of the first tile to swap
     */
    private int col1;
    /**
     * The row of the second tile to swap
     */
    private int row2;
    /**
     * The column of the second tile to swap
     */
    private int col2;

    /**
     * Initialize a new move, with tile coordinates (row1, col1) and (row2, col2)
     *
     * @param row1 The row of the first tile to swap
     * @param col1 The column of the first tile to swap
     * @param row2 The row of the second tile to swap
     * @param col2 The column of the second tile to swap
     */
    public Move(int row1, int col1, int row2, int col2) {
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = row2;
        this.col2 = col2;
    }

    /**
     * Return row1
     *
     * @return row1
     */
    public int getRow1() {
        return row1;
    }

    /**
     * Return row2
     *
     * @return row2
     */
    public int getRow2() {
        return row2;
    }

    /**
     * Return col1
     *
     * @return col1
     */
    public int getCol1() {
        return col1;
    }

    /**
     * Return col2
     *
     * @return col2
     */
    public int getCol2() {
        return col2;
    }

    public int getVerticalDistance(){
        return Math.abs(row2 - row1);
    }

    /**
     * Return a string representation of this move
     *
     * @return string representation of this move
     */
    @Override
    public String toString() {
        return "Swap the tile at (" + row1 + "," + col1 + ") with the tile at " +
                "(" + row2 + "," + col2 + ")";
    }

}
