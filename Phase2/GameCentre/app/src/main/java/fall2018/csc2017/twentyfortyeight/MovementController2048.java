package fall2018.csc2017.twentyfortyeight;

import android.content.Context;
import android.widget.Toast;

//import fall2018.csc2017.slidingtiles.BoardManager;


public class MovementController2048 {

    private BoardManager2048 boardManager = null;

    public MovementController2048() {
    }

    public void setBoardManager(BoardManager2048 boardManager) {
        this.boardManager = boardManager;
    }

    //    public void processTapMovement(Context context, int instruction, boolean display) {
//        if (boardManager.isValidMove(instruction)) {
//            boardManager.touchMove(instruction);
//            if (boardManager.gameFinished()) {
//                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
//            }
//        } else if (!this.boardManager.getBoardStatus()){
//            Toast.makeText(context, "Press back button to exit", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
//        }
//    }
    public void processSwipeMovement(Context context, String move, boolean display) {
        if (boardManager.isValidMover(move)) {
            boardManager.touchMove(move);
            if (boardManager.gameFinished()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            } else if (boardManager.gameOver()) {
                Toast.makeText(context, "YOU LOSE!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, move, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "I can't let you do that starfox", Toast.LENGTH_SHORT).show();
        }
    }
}
