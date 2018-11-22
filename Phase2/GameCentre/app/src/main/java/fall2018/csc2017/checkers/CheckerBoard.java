package fall2018.csc2017.checkers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.abstractClasses.GameBoard;
import fall2018.csc2017.slidingtiles.Move;

public class CheckerBoard extends GameBoard<CheckerTile> implements Serializable{
    /**
     * The position of the selectedTile, equals -1 if no tile is selected
     */
    private int selectedTilePos = -1;

    public CheckerBoard(List<CheckerTile> tiles, int boardSize){
        super(tiles, boardSize, boardSize);
    }

    /**
     * Returns the position of the selected tile
     * @return the position of the selected tile
     */
    public int getSelectedTilePos(){
        return selectedTilePos;
    }

    /**
     * Set the position of the selected tile
     * @param newSelectedTilePos the new position of the selected tile
     */
    public void setSelectedTilePos(int newSelectedTilePos){
        selectedTilePos = newSelectedTilePos;
    }

    /**
     * Swap the tiles as specified by move
     * @param move the two tiles to be swapped
     */
    public void swapTiles(Move move){
        CheckerTile tempTile = new CheckerTile(this.tiles[move.getRow1()][move.getCol1()].getId(),
                this.tiles[move.getRow1()][move.getCol1()].getBackground());

        this.tiles[move.getRow1()][move.getCol1()] = this.tiles[move.getRow2()][move.getCol2()];
        this.tiles[move.getRow2()][move.getCol2()] = tempTile;

        setChanged();
        notifyObservers();
    }

    /**
     * Replace piece with a blank tile, which means it died
     * @param row row of adding piece
     * @param col col of adding piece
     */
    public void destroyPiece(int row, int col){
        this.tiles[row][col] = new CheckerTile(0);
    }

    /**
     * Replace the blank space at position with a piece, who's colour is specified by turn
     * @param row row of adding piece
     * @param col col of adding piece
     * @param turn true if it is player 1's turn
     */
    public void addPiece(int row, int col, boolean turn){
        //TODO: Add a way to add KING checkers back in
        if(turn){
            this.tiles[row][col] = new CheckerTile(2);
        } else {
            this.tiles[row][col] = new CheckerTile(1);
        }
    }

    /**
     * Replace the piece at position with a king of the color specified by turn
     * @param position the position with which to replace the pieve
     * @param turn true if it is player 1's turn
     */
    public void addKing(int position, boolean turn){
        //TODO: I don't think we need a seperate method for what looks like very similar to addPiece
    }

    /**
     * Toggle the highlighting of the spaces in positions
     * @param positions array of positions to highlight/dehighlight
     */
    public void highlight(ArrayList<Integer> positions){

    }

    public void turnOffHighlight(){

    }
}
