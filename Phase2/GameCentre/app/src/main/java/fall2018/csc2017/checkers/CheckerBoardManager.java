package fall2018.csc2017.checkers;

import java.io.Serializable;
import java.util.List;

import fall2018.csc2017.Interfaces.ManageableBoard;
import fall2018.csc2017.slidingtiles.Board;
import fall2018.csc2017.slidingtiles.Score;
import fall2018.csc2017.slidingtiles.Tile;


public class CheckerBoardManager implements Serializable, ManageableBoard {

    private Board board;

    public CheckerBoardManager(){
        refreshBoard();
    }

    public void refreshBoard(){

    }

    public boolean gameFinished(){
        return false;
    }

    public boolean isValidTap(int position){
        return false;
    }

    public Board getBoard(){
        return null;
    }

    public void touchMove(int position){

    }

    public Score getBoardScore(){
        return null;
    }

    public boolean checkValid(List<Tile> list){
        return false;
    }



}
