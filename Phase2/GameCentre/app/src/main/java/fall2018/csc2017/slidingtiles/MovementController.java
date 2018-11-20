package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;


public class MovementController {

    private BoardManager boardManager = null;

    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int instruction, boolean display) {
        if (boardManager.isValidMove(instruction)) {
            boardManager.touchMove(instruction);
            if (boardManager.gameFinished()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else if (!this.boardManager.getBoardStatus()){
            Toast.makeText(context, "Press back button to exit", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
