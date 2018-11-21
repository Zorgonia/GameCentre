package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;

import fall2018.csc2017.Interfaces.TappableManager;


public class MovementController {

    private TappableManager boardManager = null;

    public MovementController() {
    }

    public void setBoardManager(TappableManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean display) {
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
