package fall2018.csc2017.checkers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.Interfaces.Manageable;
import fall2018.csc2017.Interfaces.TappableManager;
import fall2018.csc2017.slidingtiles.Board;
import fall2018.csc2017.slidingtiles.Move;
import fall2018.csc2017.slidingtiles.Score;
import fall2018.csc2017.slidingtiles.Tile;


public class CheckerBoardManager implements Serializable, TappableManager {

    /**
     * the Board being managed
     */
    private CheckerBoard board;
    /**
     * The size of the square board
     */
    final int BOARD_SIZE = 8;
    /**
     * Id of a highlighted tile
     */
    final int HIGHLIGHT_ID = 5;
    /**
     * True when board can be interacted with
     */
    private boolean activeStatus = true;
    /**
     * True when it is player 1's turn
     */
    private int turnColour = 1;

    /**
     * True when the player is able to select a checker, false when the player is able to select
     * where the selected checker can move
     */
    private boolean movePhase1 = true;

    /**
     * Create a new CheckerBoardManager
     */
    public CheckerBoardManager(){
        refreshBoard();
    }

    /**
     * Return this CheckerBoardManager's Board
     * @return the board
     */
    public CheckerBoard getBoard(){
        return board;
    }

    /**
     * Reset the board to the starting position
     */
    public void refreshBoard(){
        List<CheckerTile> tiles = new ArrayList<>();
        addCheckers(tiles, 2);
        addBlankRow(tiles);
        addCheckers(tiles, 1);
        board = new CheckerBoard(tiles, BOARD_SIZE);

    }

    /**
     * Add a blank row of CheckerTiles to tiles
     * @param tiles the list of tiles
     */
    private void addBlankRow(List<CheckerTile> tiles) {
        for (int i = 0; i < 2*BOARD_SIZE; i++){
            tiles.add(new CheckerTile(0));
        }
    }


    private void addCheckers(List<CheckerTile> tiles, int colour){
        for(int row = 0; row < 3; row++){
//          This second for loop is a bit weird.
//          I make it so that it starts off even (colour - 1) = 0 for red checkers
//          and odd (colours - 1) = 1 for black. Looks weird however
            for(int col = colour - 1; col < BOARD_SIZE + colour - 1;  col++){
                if((row + col)% 2 == 1) {
                    tiles.add(new CheckerTile(colour));
                }else{
                    tiles.add(new CheckerTile(0));
                }
            }
        }
    }

    /**
     * Return true if a game is complete, meaning one side has no more pieces or there is a draw
     * @return true if game is complete, false otherwise
     */
    public boolean gameFinished(){
        //TODO: also check for a draw
        boolean player1done = true;
        boolean player2done = true;
        for (CheckerTile t : getBoard()){
            if (t.getId() == 1 || t.getId() == 3){
                player1done = false;
            } else if (t.getId() == 2 || t.getId() == 4){
                player2done = false;
            }
        }
        return player1done || player2done;
    }

    /**
     * Return true if a user taps on one of their own pieces they can move, or a place that
     * piece can move to after tapping one of their own pieces
     * @param position the tile to check
     * @return true if tap is valid, false otherwise
     */
    public boolean isValidTap(int position){
        int row = position / board.getNumRows();
        int col = position % board.getNumCols();
        if (!activeStatus){
            return false;
        }
        if (movePhase1){
            ArrayList<Integer> captures = findCaptures();
            return (board.getTileAt(row, col).getId() == turnColour ||
                    board.getTileAt(row, col).getId() == turnColour + 2) &&
                    (captures.size() == 0 || captures.contains(position));
            //true if user taps on their own piece, and that piece can perform a capture or no
            //pieces can perform a capture
        } else {
            return true; //always return true on phase 2, because either move is made, or tile is
            //unselected
        }
    }

    /**
     * Process a checker move by the player, swapping and replacing tiles as appropriate
     * @param position the position being moved to
     */
    public void touchMove(int position){
        int row = position / board.getNumRows();
        int col = position % board.getNumCols();
        if (movePhase1){
            ArrayList<Integer> potentialMoves = findPotentialMoves(position);
            if (potentialMoves.size() > 0){
                board.setSelectedTilePos(position);
                board.highlight(potentialMoves);
                movePhase1 = false;
                //TODO make it so that if piece can capture, has to capture by only highlighting that spot
            }
        } else if(board.getTileAt(row, col).getId() == HIGHLIGHT_ID) {
            board.turnOffHighlight();
            processMove(row, col);
            board.setSelectedTilePos(-1);
            movePhase1 = true;
            switchTurn();
            if (gameFinished()){
                setBoardToInactive();
            }
        } else {
            board.turnOffHighlight();
            board.setSelectedTilePos(-1);
            movePhase1 = true;
        }

    }

    /**
     * Process a checker moving from the selected tile position to the space at row, col, capturing
     * another checker if possible
     * @param row row of the space to move to
     * @param col column of the space to move to
     */
    public void processMove(int row, int col){
        Move move = new Move(row, col, board.getSelectedTilePos() / board.getNumRows(),
                board.getSelectedTilePos() % board.getNumCols());
        board.swapTiles(move);
        if (move.getVerticalDistance() == 2){
            board.destroyPiece(Math.max(move.getRow1(), move.getRow2()) - 1,
                    Math.max(move.getCol1(), move.getCol2()) - 1 );
        }
    }

    private void switchTurn() {
        turnColour = getEnemyColour();
    }

    private int getEnemyColour() {
        if (turnColour == 1){
            return 2;
        } else{
            return 1;
        }
    }

    /**
     * Get score of the board in terms of number of moves of player 1
     * @return score of the board
     */
    public Score getBoardScore(){
        return null;
    }

    /**
     * Return true if the board is able to be interacted with
     * @return true if board is able to be interacted with
     */
    public boolean getBoardStatus() {
        return activeStatus;
    }

    /**
     * Return a list of positions of pieces that can perform a capture on this turn. Return an
     * empty list if no such pieces exist
     * @return List of positions of pieces that can perform a capture
     */
    private ArrayList<Integer> findCaptures(){
        ArrayList<Integer> captures = new ArrayList<>();
        return captures;
        //TODO this is hard to implement, do it eventually. returns a list of size 0 for now
    }



    /**
     * Return a list of positions that the piece at position can move to
     * @param position the position of the piece
     * @return ArrayList of positions that can be moved to
     */
    private ArrayList<Integer> findPotentialMoves(int position){
        ArrayList<Integer> potentialMoves = new ArrayList<>();
        int row = position / board.getNumRows();
        int col = position % board.getNumCols();
        int tileId = board.getTileAt(row, col).getId();
        if (tileId == 1){
            addMovesForward(position, row, col, potentialMoves);
        }
        else if (tileId == 2){
            addMovesBackward(position, row, col, potentialMoves);
        }
        else if (tileId == 3 || tileId == 4){
            addMovesForward(position, row, col, potentialMoves);
            addMovesBackward(position, row, col, potentialMoves);
        }
        return potentialMoves;
    }
    //TODO allow kings to be taken
    private void addMovesForward(int position, int row, int col, ArrayList<Integer> potentialMoves){
        int enemyColour = getEnemyColour();
        if (row > 0 && col > 0 && board.getTileAt(row - 1, col - 1).getId() == 0){
            potentialMoves.add(position - BOARD_SIZE - 1);
        }
        if (row > 0 && col < 7 && board.getTileAt(row - 1, col + 1).getId() == 0){
            potentialMoves.add(position - BOARD_SIZE + 1);
        }
        if (row > 1 && col > 1 && board.getTileAt(row - 1, col - 1).getId() == enemyColour
                && board.getTileAt(row - 2, col - 2).getId() == 0){
            potentialMoves.add(position - (BOARD_SIZE*2) - 2);
        }
        if (row > 1 && col < 6 && board.getTileAt(row - 1, col + 1).getId() == enemyColour
                && board.getTileAt(row - 2, col + 2).getId() == 0){
            potentialMoves.add(position - (BOARD_SIZE*2) + 2);
        }
    }

    //TODO allow kings to be taken
    private void addMovesBackward(int position, int row, int col,
                                  ArrayList<Integer> potentialMoves){
        int enemyColour = getEnemyColour();
        if (row < 7 && col > 0 && board.getTileAt(row + 1, col - 1).getId() == 0){
            potentialMoves.add(position + BOARD_SIZE - 1);
        }
        if (row < 7 && col < 7 && board.getTileAt(row + 1, col + 1).getId() == 0){
            potentialMoves.add(position + BOARD_SIZE + 1);
        }
        if (row < 6 && col > 1 && board.getTileAt(row + 1, col - 1).getId() == enemyColour
                && board.getTileAt(row + 2, col - 2).getId() == 0){
            potentialMoves.add(position + BOARD_SIZE*2 - 2);
        }
        if (row < 6 && col < 6 && board.getTileAt(row + 1, col + 1).getId() == enemyColour
                && board.getTileAt(row + 2, col + 2).getId() == 0){
            potentialMoves.add(position + BOARD_SIZE*2 + 2);
        }
    }



    public void setBoardToInactive() {
        activeStatus = false;
    }
}
