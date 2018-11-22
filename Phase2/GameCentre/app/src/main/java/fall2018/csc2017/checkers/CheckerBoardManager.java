package fall2018.csc2017.checkers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.Interfaces.Manageable;
import fall2018.csc2017.Interfaces.TappableManager;
import fall2018.csc2017.slidingtiles.Board;
import fall2018.csc2017.slidingtiles.R;
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
     * True when board can be interacted with
     */
    private boolean activeStatus = true;
    /**
     * True when it is player 1's turn
     */
    private boolean player1Turn = true;

//    /**
//     * True when the player is able to select a checker, false when the player is able to select
//     * where the selected checker can move
//     */
//    private boolean movePhase1 = true;
//    @@@@Instead, I'ma try to use an int instead to store a position. if position = null, movephase1 = false.

    private Integer movingChecker = null;

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
//        This is quite smelly. I refactored these methods
//        addRowVariation1(tiles, 1);
//        addRowVariation2(tiles, 1);
//        addRowVariation1(tiles, 1);
//        addBlankRow(tiles);
//        addRowVariation1(tiles, 2);
//        addRowVariation2(tiles, 2);
//        addRowVariation1(tiles, 2);
        addCheckers(tiles, 1);
        addBlankRow(tiles);
        addCheckers(tiles, 2);
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

    /**
     * Add rows of checkers in the pattern of blank then checker, or checker then blank. etc
     * @param tiles the list of tiles
     * @param colour the colour of the pieces added
     */
    private void addCheckers(List<CheckerTile> tiles, int colour){
        for(int row = 0; row < BOARD_SIZE; row++){
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

//
//    /**
//     * Add a row of checkers in the pattern of blank, checker, blank, checker, etc
//     * @param tiles the list of tiles
//     * @param colour the colour of the pieces added
//     */
//    private void addRowVariation1(List<CheckerTile> tiles, int colour) {
//
//
//        for(int i = 0; i < BOARD_SIZE/2; i++){
//            tiles.add(new CheckerTile(0));
//            tiles.add(new CheckerTile(colour));
//        }
//    }
//
//    /**
//     * Add a row of checkers in the pattern of checker, blank, checker, blank, etc
//     * @param tiles the list of tiles
//     * @param colour the colour of the pieces added
//     */
//    private void addRowVariation2(List<CheckerTile> tiles, int colour) {
//        for(int i = 0; i < BOARD_SIZE/2; i++){
//            tiles.add(new CheckerTile(colour));
//            tiles.add(new CheckerTile(0));
//        }
//    }

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
        //note: we can use a false result from this function to "cancel" a tap on a piece if the user wishes to move a different piece
        /*
        Summary:

        This requires a new instance of type int 'MovingChecker',
        This stores the checker position that you are about to move.

        Pseudocode:
        if you tapped a position of a checker and it is your checker:
            if (MovingChecker == position)
                # If you selected a checker before, you now canceled it by tapping the same checker
                MovingChecker == null;
            else if (MovingChecker == null)
                # If there is no position stored
                MovingChecker = position;
            return FALSE

            #the return false is a bit redundant since there's a return false at the end,
            but it's here for logical understanding,
            that while your tap is valid, movementController will take a false and will not execute touchmove

        else if position you tapped is NOT a checker and MovingChecker != null
            #if you tapped some other space and you tapped a checker before
            if (the position is 1 corner distance away)
                NextPlayersTurn();
                # not actual method, just pseudo, though could be method
                MovingChecker = null;
                return true
            else if (the position is 2 corner distance away from movingChecker)
                if (there exists a checker in between position and movingChecker)
                    MovingChecker = null;
                    CheckMoves();
                    #check if there exists other checkers to eat.
                    If so, don't switch turns. Otherwise, switch turn.

                    return true
        return false
         */
        int row = position / BOARD_SIZE;
        int col = position % BOARD_SIZE;
//        final int blankId = 0;
        int tileBackground = board.getTileAt(row + 1, col).getBackground();

        boolean p1ValidTap = (tileBackground == R.drawable.tile_checkers_red ||
                tileBackground == R.drawable.tile_checkers_red_king) &&
                player1Turn;

        boolean p2ValidTap = (tileBackground == R.drawable.tile_checkers_black ||
                              tileBackground == R.drawable.tile_checkers_black_king) &&
                              !player1Turn;

        if(p1ValidTap || p2ValidTap){
            if (movingChecker == position)
//              If you selected a checker before, you now canceled it by tapping the same checker
                movingChecker = null;
            else if (movingChecker == null)
//                # If there is no position stored
                movingChecker = position;
        }
        return false;
    }

    /**
     * Process a checker move by the player, swapping and replacing tiles as appropriate
     * @param position the position being moved to
     */
    public void touchMove(int position){

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
        return false;
    }

    /**
     * Return a list of positions of pieces that can perform a capture on this turn. Return an
     * empty list if no such pieces exist
     * @return List of positions of pieces that can perform a capture
     */
    private ArrayList<Integer> findCaptures(){
        return null;
    }



    /**
     * Return a list of positions that the piece at position can move to
     * @param position
     * @return Array of positions that can be moved to
     */
    private ArrayList<Integer> findPotentialMoves(int position){
        return null;
    }


    public void setBoardToInactive() {
        activeStatus = false;
    }
}
