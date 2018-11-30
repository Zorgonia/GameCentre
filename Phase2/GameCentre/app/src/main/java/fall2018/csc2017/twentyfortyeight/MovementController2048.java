package fall2018.csc2017.twentyfortyeight;

import android.content.Context;
import android.widget.Toast;

/**
 * A movement controller class that takes movements from the gesture detector and sends them to boardManager
 */
public class MovementController2048 {

    /**
     * Board manager to interact with
     */
    private BoardManager2048 boardManager = null;

    /**
     * A default constructor
     */
    MovementController2048() {
    }

    /**
     * Setter for boardManager
     * @param boardManager board manager to set to
     */
    public void setBoardManager(BoardManager2048 boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * A method that takes in a swipe to send to board if it's a valid move
     * @param context the context to display things to if needed (like a toast)
     * @param move the move that it was given
     */
    void processSwipeMovement(Context context, int move) {
        if (!boardManager.getBoardStatus()) {
            Toast.makeText(context, "The game is over, press back to return to the main menu", Toast.LENGTH_SHORT).show();
        } else if (boardManager.isValidTap(move)) {
            boardManager.touchMove(move);
            if (boardManager.gameFinished()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            } else if (boardManager.gameOver()) {
                Toast.makeText(context, "YOU LOSE!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
        }
    }
}
