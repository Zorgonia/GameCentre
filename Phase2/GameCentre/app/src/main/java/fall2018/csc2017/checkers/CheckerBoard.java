package fall2018.csc2017.checkers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.ParentClasses.GameBoard;
import fall2018.csc2017.Move;

public class CheckerBoard extends GameBoard<CheckerTile> implements Serializable{
    /**
     * The position of the selectedTile, equals -1 if no tile is selected
     */
    private int selectedTilePos = -1;

    /**
     * Id of a highlighted tile
     */
    final int HIGHLIGHT_ID = 5;

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
     * Replace the space at row, col with a piece, with id id
     * @param row row of adding piece
     * @param col col of adding piece
     * @param backId: id of piece being added
     */
    public void addPiece(int row, int col, int backId){
        this.tiles[row][col] = new CheckerTile(backId);
    }

    /**
     * Highlight the spaces in positions
     * @param positions array of positions to highlight
     */
    public void highlight(ArrayList<Integer> positions){
        for (Integer i : positions){
            int row = i / getNumRows();
            int col = i % getNumCols();
            this.tiles[row][col] = new CheckerTile(HIGHLIGHT_ID);
        }

    }

    /**
     * Turn all highlights spaces back into blank spaces
     */
    public void turnOffHighlight(){
        for (int row = 0; row < getNumRows(); row++){
            for(int col = 0; col < getNumCols(); col++){
                if (getTileAt(row, col).getId() == HIGHLIGHT_ID){
                    tiles[row][col] = new CheckerTile(0);
                }
            }
        }
    }

    /**
     * Tell the observer board has been updated
     */
    public void update(){
        setChanged();
        notifyObservers();
    }
}
