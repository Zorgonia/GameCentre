package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;

import fall2018.csc2017.Interfaces.ManagerInterface;


/**
 * A movement controller for sliding tiles and checkers
 */
public class MovementController {

    /**
     * Which BoardManager to send moves to
     */
    private ManagerInterface boardManager = null;

    /**
     * A default constructor
     */
    public MovementController() {
    }

    /**
     * Setter for the board manager
     * @param boardManager the board manager to set to
     */
    public void setBoardManager(ManagerInterface boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Takes a move from the gesture detector and if it's valid gives the move to the boardmanager
     * @param context the context to draw things on (ie for toasts)
     * @param position position of the move
     */
    public void processTapMovement(Context context, int position) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.gameFinished()) {
                Toast.makeText(context, "Game Complete", Toast.LENGTH_SHORT).show();
            }
        } else if (!this.boardManager.getBoardStatus()){
            Toast.makeText(context, "Press back button to exit", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
