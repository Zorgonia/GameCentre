package fall2018.csc2017.twentyfortyeight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.Interfaces.Manageable;
import fall2018.csc2017.slidingtiles.Score;

public class BoardManager2048 implements Manageable, Serializable {

    /**
     * The board being managed.
     */
    private Board2048 board;

    /**
     * false indicates game is finished and board isn't active any longer
     */
    private boolean activeStatus = true;

    /**
     * Return the current board.
     */
    public Board2048 getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    BoardManager2048() {
        refreshBoard();
    }

    /**
     * Changes the current board to a new 2048 game
     */
    // TODO: change method to allow for complexity
    public void refreshBoard(){
        List<Tile2048> tiles = new ArrayList<>();
        for (int x = 0; x != 16; x++) {
            tiles.add(new Tile2048(0));
        }
        this.board = new Board2048(tiles, 4, 4);
        this.board.placeRandomTile();
    }

    /**
     * Returns true if no moves can be made or a tile of 2048 is achieved
     * No moves can be made when no 2 tiles can be combined adjacently
     * @return true if game finishes, false otherwise
     */
    public boolean gameFinished(){
        boolean gameFin = true;
        mainLoop:
        for (int row = 0; row != 4; row++) {
            for (int col = 0; col != 4; col++) {
                if (board.getTileAt(row,col).getId() == 2048){
                    gameFin = true;
                    break mainLoop;
                } else if (board.hasEqualAdjacentTile(row,col)){
                    gameFin = false;
                    break mainLoop;
                }
            }
        }
        return gameFin;
    }

    // TODO: implement score and getBoardScore
    public Score getBoardScore(){
        Score score = new Score(0);
        return score;
    }

    // TODO: Not sure if we need this for 2048
    public boolean isValidTap(int position){
        return true;
    }


    // ignores position
    // TODO: remove touchMove from the Manageable interface
    public void touchMove(String direction){
        if (direction.equals("up") || direction.equals("down")){
            for (int col = 0; col != 4; col++){
                removeDoublesCol(col, direction);
                adjustTilesCol(col,direction);
            }
        } else { // (direction.equals("left") || direction.equals("right"))
            for (int row = 0; row != 4; row++){
                removeDoublesRow(row, direction);
                adjustTilesRow(row,direction);
            }
        }
        board.placeRandomTile();
    }

    // TODO: for the following 2 methods, attempt to shorten them or combine them
    /**
     * Adjusts the tiles in the given column position according to the direction
     * Precondition: vertDirection must be either "up" or "down"
     * @param col the column index
     * @param vertDirection specifying "up" or "down"
     */
    private void adjustTilesCol(int col, String vertDirection){
        if (vertDirection.equals("up")){
            int row = 0;
            int emptyIndex = -1;
            int id;
            while (row != 4){
                id = board.getTileAt(row,col).getId();
                // if current tile is empty tile, emptyIndex = current row
                if (board.getTileAt(row,col).getId() == 0){
                    emptyIndex = row;
                }
                // if current tile isn't an empty tile and empty index exists
                if (emptyIndex != -1 && id != 0){
                    board.placeNewTileAt(id, emptyIndex, col);
                    board.removeTileAt(row,col);
                    emptyIndex = -1;
                }
                row ++;
            }
        }
    }

    /**
     * Adjusts the tiles in the given column position according to the direction
     * Precondition: vertDirection must be either "up" or "down"
     * @param row the row index
     * @param vertDirection specifying "left" or "right"
     */
    private void adjustTilesRow(int row, String vertDirection){
        if (vertDirection.equals("up")){
            int col = 0;
            int emptyIndex = -1;
            int id;
            while (col != 4){
                id = board.getTileAt(row,col).getId();
                // if current tile is empty tile, emptyIndex = current col
                if (board.getTileAt(row,col).getId() == 0){
                    emptyIndex = col;
                }
                // if current tile isn't an empty tile and empty index exists
                if (emptyIndex != -1 && id != 0){
                    board.placeNewTileAt(id, row, emptyIndex);
                    board.removeTileAt(row,col);
                    emptyIndex = -1;
                }
                col ++;
            }
        }
    }

    void removeDoublesCol(int col, String VertDirection){
        if (VertDirection.equals("up")){
            int row = 0;
            while (row < 4 - 1){
                int value = board.getTileAt(row,col).getId();
                if (board.getTileAt(row,col).equals(board.getTileAt(row + 1,col))){
                    board.placeNewTileAt(2*value,row,col);
                    board.removeTileAt(row + 1,col);
                    row += 2;
                } else {
                    row += 1;
                }
            }
        } else { // direction is down
            int row = 4 - 1;
            while (row > 0){
                int value = board.getTileAt(row,col).getId();
                if (board.getTileAt(row,col).equals(board.getTileAt(row - 1,col))){
                    board.placeNewTileAt(2*value,row,col);
                    board.removeTileAt(row - 1,col);
                    row -= 2;
                } else {
                    row -= 1;
                }
            }
        }
    }

    void removeDoublesRow(int row, String SideDirection){
        if (SideDirection.equals("left")){
            int col = 0;
            while (col < 4 - 1){
                int value = board.getTileAt(row,col).getId();
                if (board.getTileAt(row,col).equals(board.getTileAt(row,col+1))){
                    board.placeNewTileAt(2*value,row,col);
                    board.removeTileAt(row,col+1);
                    col += 2;
                } else {
                    col += 1;
                }
            }
        } else {
            int col = 4 - 1;
            while (col > 0){
                int value = board.getTileAt(row,col).getId();
                if (board.getTileAt(row,col).equals(board.getTileAt(row,col-1))){
                    board.placeNewTileAt(2*value,row,col);
                    board.removeTileAt(row,col-1);
                    col -= 2;
                } else {
                    col -= 1;
                }
            }
        }
    }

}
