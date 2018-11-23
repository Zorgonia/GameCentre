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

    private CheckerUndoStack undoStack;
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
     * True when a player has taken a piece, and is able to take another
     */
    private boolean primedCapture = false;

    private boolean availableCapture = false;

    /**
     * Create a new CheckerBoardManager
     */
    public CheckerBoardManager(){
        refreshBoard();
        undoStack = new CheckerUndoStack();
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
            if (captures.size() > 0){
                availableCapture = true;
            }
            else {
                availableCapture = false;
            }
            return (board.getTileAt(row, col).getId() == turnColour ||
                    board.getTileAt(row, col).getId() == turnColour + 2) &&
                    (captures.size() == 0 || captures.contains(position));
            //true if user taps on their own piece, and that piece can perform a capture or no
            //pieces can perform a capture
        } else if (primedCapture) {
            return board.getTileAt(row, col).getId() == HIGHLIGHT_ID;
        } else{
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
            ArrayList<Integer> potentialMoves = findPotentialMoves(position, availableCapture);
            if (potentialMoves.size() > 0){
                board.setSelectedTilePos(position);
                board.highlight(potentialMoves);
                movePhase1 = false;
            }
        } else if(board.getTileAt(row, col).getId() == HIGHLIGHT_ID) {
            board.turnOffHighlight();
            processMove(row, col);
            if (!primedCapture){
                movePhase1 = true;
                switchTurn();
            }
            if (gameFinished()){
                setBoardToInactive();
            }
        } else {
            board.turnOffHighlight();
            board.setSelectedTilePos(-1);
            movePhase1 = true;
        }
        board.update();

    }

    /**
     * Process a checker moving from the selected tile position to the space at row, col, capturing
     * another checker if possible, and kinging if possible
     * @param row row of the space to move to
     * @param col column of the space to move to
     */
    public void processMove(int row, int col){
        Move move = new Move(row, col, board.getSelectedTilePos() / board.getNumRows(),
                board.getSelectedTilePos() % board.getNumCols());
        board.swapTiles(move);
        undoStack.add(move);
        if (((row == 0 && turnColour == 1 ) ||(row == 7 && turnColour == 2)
                && board.getTileAt(row, col).getId() == turnColour)){
            board.addPiece(row, col, turnColour + 2);
            undoStack.addKinged(true);
        }
        else {
            undoStack.addKinged(false);
        }
        if (move.getVerticalDistance() == 2){
            int midRow = Math.max(move.getRow1(), move.getRow2()) - 1;
            int midCol = Math.max(move.getCol1(), move.getCol2()) - 1;
            undoStack.addCapture(midRow, midCol, board.getTileAt(midRow, midCol).getId());
            board.destroyPiece(midRow, midCol);
            if (findPotentialMoves(row*8 + col, true).size() > 0){
                primedCapture = true;
                board.setSelectedTilePos(row*8 + col);
                board.highlight(findPotentialMoves(row*8 + col, true));
            } else {
                primedCapture = false;
                board.setSelectedTilePos(-1);
            }
            undoStack.addIsPrimedCapture(primedCapture);
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

    public int getTurnColour(){
        return turnColour;
    }

    /**
     * Return a list of positions of pieces that can perform a capture on this turn. Return an
     * empty list if no such pieces exist
     * @return List of positions of pieces that can perform a capture
     */
    private ArrayList<Integer> findCaptures(){
        ArrayList<Integer> captures = new ArrayList<>();
        for(int row = 0; row < board.getNumRows(); row++){
            for(int col = 0; col < board.getNumCols(); col++){
                int id = board.getTileAt(row, col).getId();
                if ((id == turnColour || id == turnColour + 2) &&
                        findPotentialMoves(row*8 + col, true).size() > 0){
                    captures.add(row*8 + col);
                }
            }
        }
        return captures;
    }



    /**
     * Return a list of positions that the piece at position can move to
     * Return only a list of positions that the piece can move to that involve capturing an
     * enemy piece if onlyCapture is true
     * @param position the position of the piece
     * @param onlyCapture return only a list of potential captures.
     * @return ArrayList of positions that can be moved to
     */
    private ArrayList<Integer> findPotentialMoves(int position, boolean onlyCapture){
        ArrayList<Integer> potentialMoves = new ArrayList<>();
        int row = position / board.getNumRows();
        int col = position % board.getNumCols();
        int tileId = board.getTileAt(row, col).getId();
        if (tileId == 1){
            addMovesForward(position, row, col, potentialMoves, onlyCapture);
        }
        else if (tileId == 2){
            addMovesBackward(position, row, col, potentialMoves, onlyCapture);
        }
        else if (tileId == 3 || tileId == 4){
            addMovesForward(position, row, col, potentialMoves, onlyCapture);
            addMovesBackward(position, row, col, potentialMoves, onlyCapture);
        }
        return potentialMoves;
    }
    private void addMovesForward(int position, int row, int col, ArrayList<Integer> potentialMoves,
    boolean onlyCapture){
        int enemyColour = getEnemyColour();
        if (row > 0 && col > 0 && board.getTileAt(row - 1, col - 1).getId() == 0 &&
                !onlyCapture){
            potentialMoves.add(position - BOARD_SIZE - 1);
        }
        if (row > 0 && col < 7 && board.getTileAt(row - 1, col + 1).getId() == 0 &&
                !onlyCapture){
            potentialMoves.add(position - BOARD_SIZE + 1);
        }
        if (row > 1 && col > 1 && (board.getTileAt(row - 1, col - 1).getId() == enemyColour
                || board.getTileAt(row - 1, col - 1).getId() == enemyColour + 2)
                && board.getTileAt(row - 2, col - 2).getId() == 0){
            potentialMoves.add(position - (BOARD_SIZE*2) - 2);
        }
        if (row > 1 && col < 6 && (board.getTileAt(row - 1, col + 1).getId() == enemyColour
                || board.getTileAt(row - 1, col + 1).getId() == enemyColour + 2)
                && board.getTileAt(row - 2, col + 2).getId() == 0){
            potentialMoves.add(position - (BOARD_SIZE*2) + 2);
        }
    }

    private void addMovesBackward(int position, int row, int col,
                                  ArrayList<Integer> potentialMoves, boolean onlyCapture){
        int enemyColour = getEnemyColour();
        if (row < 7 && col > 0 && board.getTileAt(row + 1, col - 1).getId() == 0 &&
                !onlyCapture){
            potentialMoves.add(position + BOARD_SIZE - 1);
        }
        if (row < 7 && col < 7 && board.getTileAt(row + 1, col + 1).getId() == 0 &&
                !onlyCapture){
            potentialMoves.add(position + BOARD_SIZE + 1);
        }
        if (row < 6 && col > 1 && (board.getTileAt(row + 1, col - 1).getId() == enemyColour
                || board.getTileAt(row + 1, col - 1).getId() == enemyColour + 2)
                && board.getTileAt(row + 2, col - 2).getId() == 0){
            potentialMoves.add(position + BOARD_SIZE*2 - 2);
        }
        if (row < 6 && col < 6 && (board.getTileAt(row + 1, col + 1).getId() == enemyColour
                || board.getTileAt(row + 1, col + 1).getId() == enemyColour + 2)
                && board.getTileAt(row + 2, col + 2).getId() == 0){
            potentialMoves.add(position + BOARD_SIZE*2 + 2);
        }
    }

    /**
     * Undo the last move made in the game. In the case of forced consecutive captures, undo all of
     * them at once.
     */
    public void undo(){
        board.turnOffHighlight();
        Move undoMove = undoStack.remove();
        if (undoMove != null){
            if (undoMove.getVerticalDistance() == 2){
                undoStack.getIsPrimedCapture();
                do{
                    board.swapTiles(undoMove);
                    int[] captureData = undoStack.removeCapture();
                    board.addPiece(captureData[0] / board.getNumRows(),
                            captureData[0] % board.getNumRows(), captureData[1]);
                    processUndoKing(undoMove.getRow2(), undoMove.getCol2(), undoStack.removeKinged());
                    undoMove = undoStack.remove();
                } while(undoStack.getIsPrimedCapture());
                undoStack.addIsPrimedCapture(false);
                undoStack.add(undoMove);
            } else {
                board.swapTiles(undoMove);
                processUndoKing(undoMove.getRow2(), undoMove.getCol2(), undoStack.removeKinged());
            }
            movePhase1 = true;
            primedCapture = false;
            board.setSelectedTilePos(-1);
            switchTurn();
        }
        board.update();

    }

    public void processUndoKing(int row, int col, boolean kinged){
        if (kinged){
            board.addPiece(row, col, board.getTileAt(row, col).getId() - 2);
        }
    }


    /**
     * black moves, red moves, black captures and primes another, performs primed capture and primes
     * another, perform primes capture, red presses undo.
     * false, false, true, true, false
     * removes the first false, then undoes and removes true, then undoes and removes true, then
     * undoes and removes false. if i readded a false here, would there be an issue?
     */
    public void setBoardToInactive() {
        activeStatus = false;
    }
}
