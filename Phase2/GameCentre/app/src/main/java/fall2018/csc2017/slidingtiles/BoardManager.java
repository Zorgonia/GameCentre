package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manage a board, including swapping tiles, undoing swaps, checking for a win, and managing taps.
 */

class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * The stack of moves to be called when undo is requested
     */
    private UndoStack undoStack;

    /**
     * Complexity of current board instance, 3 + complex is numRows=numCols
     */
    private int complex = 0;

    /**
     * Score of the current board in terms of moves made
     */
    private Score score = new Score(0);

    /**
     * false indicates game is solved and board isn't active any longer
     */
    private boolean activeStatus = true;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    BoardManager() {
        refreshBoard();
        undoStack = new UndoStack();
    }

    /**
     * Refreshes the tile set of the board (for a new game)
     */
    void refreshBoard() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.getNumRows() * Board.getNumCols();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.remove(numTiles - 1);
        tiles.add(new Tile(numTiles, R.drawable.tile_blank));
        Collections.shuffle(tiles);
        this.board = new Board(tiles);
    }

    /**
     * Sets the columns and rows of board according to current complexity of board manager
     *
     * @param complexity desired complexity to be set to
     */
    public void setComplexity(int complexity) {
        complex = complexity;
        if (complexity == 2) {
            Board.setNumCols(5);
            Board.setNumRows(5);
        } else if (complexity == 1) {
            Board.setNumCols(4);
            Board.setNumRows(4);
        } else {
            Board.setNumCols(3);
            Board.setNumRows(3);
        }
    }

    /**
     * Get score of the board in terms of number of moves
     *
     * @return the Score of the board
     */
    Score getBoardScore() {
        return score;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;
        int count = 1;
        for (Tile t : getBoard()) {
            if (t.getId() != count) {
                solved = false;
            }
            count++;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / Board.getNumRows();
        int col = position % Board.getNumCols();
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.getNumRows() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.getNumCols() - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     * Update the Score
     *
     * @param position the position
     */
    void touchMove(int position) {
        Move move = null;

        // instances provided, to determine row/col of the tile tapped
        int row = position / Board.getNumRows();
        int col = position % Board.getNumCols();
        int blankId = board.numTiles();

        // The tiles surrounding the tapped tile
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.getNumRows() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.getNumCols() - 1 ? null : board.getTile(row, col + 1);

        // For whichever one around is empty, swap with that one.
        if (below != null && below.getId() == blankId) {
            move = new Move(row, col, row + 1, col);
        } else if (above != null && above.getId() == blankId) {
            move = new Move(row, col, row - 1, col);
        } else if (left != null && left.getId() == blankId) {
            move = new Move(row, col, row, col - 1);
        } else if (right != null && right.getId() == blankId) {
            move = new Move(row, col, row, col + 1);
        }
        if (move != null){
            board.swapTiles(move);
            undoStack.add(move);
        }
    }

    /**
     * Undo a move made by the player. Do nothing if move cannot be undone.
     * Score increases for Undo. This is INTENTIONAL.
     */
    public void undo() {
        Move move = undoStack.remove();
        if (move != null) {
            board.swapTiles(move);
        }
    }

    /**
     * Add 1 to the size of this BoardManager's UndoStack if add is true, subtract 1 otherwise
     * @param increment: If true, add to size, otherwise subtract
     */
    void incrementUndo(int increment){
        undoStack.incrementSize(increment);
    }

    /**
     * If limited is false, make undo have no move limit. If true, make it have a limit
     * @param limited: If false, make undo have no limit, if true make undo have a limit
     */
    void setLimited(boolean limited){
        undoStack.setLimited(limited);
    }

    /**
     * Returns the complexity of the board manager
     *
     * @return the complexity of board manager
     */
    int getComplex() {
        return complex;
    }

    /**
     * sets board activeStatus to false
     */
    void setBoardToInactive(){
        activeStatus = false;
    }

    /**
     * returns the board's active status
     * @return activeStatus: boolean
     */
    boolean getBoardStatus(){
        return activeStatus;
    }
}
