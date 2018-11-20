package fall2018.csc2017.checkers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.abstractClasses.AbstractBoard;
import fall2018.csc2017.slidingtiles.Move;
import fall2018.csc2017.slidingtiles.Tile;

public class CheckerBoard extends AbstractBoard implements Serializable{
    /**
     * The position of the selectedTile, equals -1 if no tile is selected
     */
    private int selectedTilePos = -1;

    public CheckerBoard(List<CheckerTile> tiles, int boardSize){
        super(new ArrayList<Tile>(0), boardSize, boardSize); //list is temporary so code compiles until we figure out the board/tile issue
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
        //unsure if want to use Move object or make new object
    }

    /**
     * Replace the piece at position with a blank space
     * @param position the position to replace the piece at
     */
    public void destroyPiece(int position){
        //unsure if want parameter to be "position" or "row, col"
    }

    /**
     * Replace the blank space at position with a piece, who's colour is specified by turn
     * @param position the position with which to replace the piece
     * @param turn true if it is player 1's turn
     */
    public void addPiece(int position, boolean turn){
        //this is for the undo function
    }

    /**
     * Replace the piece at position with a king of the color specified by turn
     * @param position the position with which to replace the pieve
     * @param turn true if it is player 1's turn
     */
    public void addKing(int position, boolean turn){

    }

    /**
     * Toggle the highlighting of the spaces in positions
     * @param positions array of positions to highlight/dehighlight
     */
    public void toggleHighlight(int[] positions){

    }
}
